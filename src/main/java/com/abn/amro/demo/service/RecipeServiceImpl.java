package com.abn.amro.demo.service;

import com.abn.amro.demo.dao.RecipeDao;
import com.abn.amro.demo.dto.RecipeDTO;
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
     * @param recipeDTO contain api request data
     * @return contain api response data
     */
    @Override
    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        RecipeEntity entity = modelMapper.map(recipeDTO, RecipeEntity.class);
        logger.debug("Request mapped to Entity: {}", Boolean.TRUE);
        recipeDTO = modelMapper.map(recipeDao.save(entity), RecipeDTO.class);
        logger.debug("Entity mapped to Response: {}", Boolean.TRUE);
        return recipeDTO;
    }

    /**
     * Method to update the persisted recipe by mapping the request DTO to entity,
     * saving the updated entity and finally mapping the persisted entity to the response DTO.
     * If recipe not found then user friendly error message is returned.
     *
     * @param recipeDTO contain api request data
     * @return contain api response data
     */
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        Optional<RecipeEntity> recipeEntity = recipeDao.findById(recipeDTO.getId());
        logger.debug("Recipe found to update {}", recipeEntity.isPresent());
        if (!recipeEntity.isPresent())
            throw new RecipeProcessingException("Recipe not found to update with id: " + recipeDTO.getId(),
                    HttpStatus.NOT_FOUND);
        RecipeEntity updatedEntity = modelMapper.map(recipeDTO, RecipeEntity.class);
        logger.debug("Request mapped to Entity: {}", Boolean.TRUE);
        recipeDTO = modelMapper.map(recipeDao.save(updatedEntity), RecipeDTO.class);
        logger.debug("Entity mapped to Response: {}", Boolean.TRUE);
        return recipeDTO;
    }

    /**
     * Method to fetch the persisted recipe based on id. Mapping the entity to response dto.
     * if not found then user friendly message is returned.
     *
     * @param id of recipe to select from records
     * @return the recipe detail with given id
     */
    public RecipeDTO selectRecipe(Long id) {
        Optional<RecipeEntity> recipeEntity = recipeDao.findById(id);
        logger.debug("Recipe found {}", recipeEntity.isPresent());
        return modelMapper.map(
                recipeEntity.orElseThrow(() -> new RecipeProcessingException("Recipe not found to with id: " + id, HttpStatus.NOT_FOUND))
                , RecipeDTO.class);

    }

    /**
     * Method to fetch all the persisted recipes, sorting the recipes based on name
     * and mapping to response DTO.
     *
     * @return all recipes present in records
     */
    @Override
    public List<RecipeDTO> selectRecipes() {
        List<RecipeEntity> recipeEntities = recipeDao.findAll();
        logger.debug("{} Recipe(s) found.", recipeEntities.size());
        Type type = new TypeToken<List<RecipeDTO>>() {
        }.getType();
        return modelMapper.map(recipeEntities, type);
    }

    /**
     * Method to delete the recipe with provided id.
     * if not found then user friendly message is returned.
     *
     * @param id to delete the recipe from records
     * @return true if recipe deleted
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