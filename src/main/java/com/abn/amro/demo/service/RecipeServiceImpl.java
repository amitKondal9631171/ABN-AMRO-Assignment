package com.abn.amro.demo.service;

import com.abn.amro.demo.dao.RecipeDao;
import com.abn.amro.demo.dto.RequestDTO;
import com.abn.amro.demo.dto.ResponseDTO;
import com.abn.amro.demo.entity.RecipeEntity;
import com.abn.amro.demo.exceptions.RecipeProcessingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeDao recipeDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseDTO addRecipe(RequestDTO requestDTO) {
        RecipeEntity entity = modelMapper.map(requestDTO, RecipeEntity.class);
        return modelMapper.map(recipeDao.save(entity), ResponseDTO.class);
    }

    public ResponseDTO updateRecipe(RequestDTO requestDTO) {
        return recipeDao.findById(requestDTO.getId()).map(entity -> {
                 return modelMapper.map(recipeDao.save(entity), ResponseDTO.class);
        }).orElseThrow(() -> new RecipeProcessingException("Resource not exist", HttpStatus.NOT_FOUND));
    }

    public ResponseDTO selectRecipe(Long id) {
        return recipeDao.findById(id).map(entity ->  {return modelMapper.map(entity, ResponseDTO.class);})
                     .orElseThrow(() -> new RecipeProcessingException("Resource not exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<ResponseDTO> selectRecipes(){
        List<RecipeEntity> entities = recipeDao.findAll();
        Type type = new TypeToken<List<ResponseDTO>>() {}.getType();
        List<ResponseDTO> listOfEntities = modelMapper.map(entities, type);
        listOfEntities.sort(ResponseDTO.compareByName);
          return listOfEntities;
    }

    public boolean deleteRecipe(Long id) {
        return recipeDao.findById(id).map( value -> {
                    recipeDao.deleteById(value.getId());
                    return true;
                }).orElseThrow(() -> new RecipeProcessingException("Resource not exist", HttpStatus.NOT_FOUND));
     }

}