package com.abn.amro.demo.dao;

import com.abn.amro.demo.entity.IngredientEntity;
import com.abn.amro.demo.entity.RecipeEntity;
import com.abn.amro.demo.exceptions.RecipeProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeDaoTest {

    @Autowired
    private RecipeDao recipeDao;

    private RecipeEntity getRecipeEntity() {
        RecipeEntity entity = new RecipeEntity();
        entity.setName("Mushroom");
        entity.setRecipeType("VEG");
        entity.setCookingInstructions("1. Boil 2 cups of water in a pot.; 2. Add 1 cup tomatoes, half inch ginger, garlic, nuts.; 3. Mix in cooking oil and cook for 15 minutes.");
        IngredientEntity ingredientEntity1 = new IngredientEntity();
        ingredientEntity1.setName("Mushroom");
        IngredientEntity ingredientEntity2 = new IngredientEntity();
        ingredientEntity2.setName("Cooking oil");
        Set<IngredientEntity> ingredients = new HashSet<IngredientEntity>();
        ingredients.add(ingredientEntity1);
        ingredients.add(ingredientEntity2);
        entity.setIngredients(ingredients);
        return entity;
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    void addRecipeTest() {
        //Given
        RecipeEntity entity = getRecipeEntity();
        //When
        RecipeEntity responseEntity = recipeDao.save(entity);
        //Then
        Assertions.assertThat(responseEntity.getId()).isPositive();
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    void selectRecipeTest() {
        //Given
        Long recipeId = 1L;
        RecipeEntity responseEntity = recipeDao.findById(recipeId).get();
        Assertions.assertThat(responseEntity.getId()).isEqualTo(recipeId);
    }

    @Test
    @Order(3)
    void selectListOfRecipesTest() {

        List<RecipeEntity> entityList = recipeDao.findAll();

        Assertions.assertThat(entityList).isNotEmpty();

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    void updateRecipeTest() {
        //Given
        Long recipeId = 1L;
        RecipeEntity persistedEntity = recipeDao.findById(recipeId).get();
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setName("Nuts");
        persistedEntity.getIngredients().add(ingredientEntity);
        RecipeEntity updatedEntity = recipeDao.save(persistedEntity);
        Assertions.assertThat(updatedEntity).isEqualTo(persistedEntity);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    void deleteRecipeTest() {
        //Given
        Long recipeId = 1L;
        Optional<RecipeEntity> responseEntity = recipeDao.findById(recipeId);
        RecipeEntity persistedEntity = responseEntity.orElseThrow(() -> new RecipeProcessingException("Recipe not found to with id: " + recipeId, HttpStatus.BAD_REQUEST));
        recipeDao.delete(persistedEntity);
        Optional<RecipeEntity> deletedEntity = recipeDao.findById(recipeId);
        NoSuchElementException thrown = assertThrows(
                NoSuchElementException.class, deletedEntity::get
        );
        assertTrue(thrown.getMessage().contains("No value present"));
    }

}

