package com.abn.amro.demo.service;

import com.abn.amro.demo.dto.RecipeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RecipeService {

    RecipeDTO addRecipe(RecipeDTO recipe);

    RecipeDTO updateRecipe(RecipeDTO recipe);

    RecipeDTO selectRecipe(Long id);

    List<RecipeDTO> selectRecipes();

    boolean deleteRecipe(Long id);
}
