package com.abn.amro.demo.service;

import com.abn.amro.demo.dto.RequestDTO;
import com.abn.amro.demo.dto.ResponseDTO;
import com.abn.amro.demo.entity.RecipeEntity;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RecipeService {
    ResponseDTO addRecipe(RequestDTO recipe);

    ResponseDTO updateRecipe(RequestDTO recipe);

    ResponseDTO selectRecipe(Long id);

    List<ResponseDTO> selectRecipes();

    boolean deleteRecipe(Long id);
}
