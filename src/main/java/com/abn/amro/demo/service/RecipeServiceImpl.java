package com.abn.amro.demo.service;

import com.abn.amro.demo.dao.RecipeDao;
import com.abn.amro.demo.dto.RequestDTO;
import com.abn.amro.demo.dto.ResponseDTO;
import com.abn.amro.demo.entity.RecipeEntity;
import com.abn.amro.demo.exceptions.RecipeProcessingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Exception handler is responsible to handle the exceptions.
 * {@link com.abn.amro.demo.exceptions.RecipeAPIExceptionHandler}
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);
    @Autowired
    private RecipeDao recipeDao;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Method to persist the entity by mapping the request DTO to entity, saving the entity and finally mapping the persisted
     * entity to the response DTO.
     *
     * @param requestDTO
     * @return
     */
    @Override
    public ResponseDTO addRecipe(RequestDTO requestDTO) {
        RecipeEntity entity = modelMapper.map(requestDTO, RecipeEntity.class);
        logger.debug("DTO mapped to Entity.");
        return modelMapper.map(recipeDao.save(entity), ResponseDTO.class);
    }

    /**
     * Method to update the persisted recipe by mapping the request DTO to entity,
     * saving the updated entity and finally mapping the persisted entity to the response DTO.
     * If recipe not found then user friendly error message is returned.
     *
     * @param requestDTO
     * @return
     */
    public ResponseDTO updateRecipe(RequestDTO requestDTO) {
        Optional<RecipeEntity> recipeEntity = recipeDao.findById(requestDTO.getId());
        logger.debug("Recipe found to update {}", recipeEntity.isPresent());
        if(!recipeEntity.isPresent())
              throw  new RecipeProcessingException("Recipe not found to update with id: " + requestDTO.getId(),
                        HttpStatus.NOT_FOUND);
        RecipeEntity updatedEntity = modelMapper.map(requestDTO, RecipeEntity.class);
        return modelMapper.map(recipeDao.save(updatedEntity), ResponseDTO.class);
    }

    /**
     * Method to fetch the persisted recipe based on id. Mapping the entity to response dto.
     * if not found then user friendly message is returned.
     * @param id
     * @return
     */
    public ResponseDTO selectRecipe(Long id) {
        Optional<RecipeEntity> recipeEntity = recipeDao.findById(id);
        logger.debug("Recipe found {}", recipeEntity.isPresent());
        return modelMapper.map(
                recipeEntity.orElseThrow(() -> new RecipeProcessingException("Recipe not found to with id: " + id, HttpStatus.NOT_FOUND))
                , ResponseDTO.class);

    }

    /**
     * Method to fetch all the persisted recipes, sorting the recipes based on name
     * and mapping to response DTO.
     * @return
     */
    @Override
    public List<ResponseDTO> selectRecipes() {
        List<RecipeEntity> recipeEntities = recipeDao.findAll();
        logger.debug("{} Recipe(s) found.", recipeEntities.size());
        Type type = new TypeToken<List<ResponseDTO>>() {
        }.getType();
        return modelMapper.map(recipeEntities, type);
    }

    /**
     * Method to delete the recipe with provided id.
     * if not found then user friendly message is returned.
     * @param id
     * @return
     */
    public boolean deleteRecipe(Long id) {
        Optional<RecipeEntity> recipeEntity = recipeDao.findById(id);
        logger.debug("Recipe found to delete {}", recipeEntity.isPresent());
        recipeDao.deleteById(recipeEntity.orElseThrow(
                () -> new RecipeProcessingException("Recipe not found to with id: " + id, HttpStatus.NOT_FOUND)
        ).getId());
        return true;
    }

}