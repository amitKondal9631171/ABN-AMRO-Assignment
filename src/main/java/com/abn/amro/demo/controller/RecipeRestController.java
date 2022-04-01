/**
 * Controller with CRUD operations
 */

package com.abn.amro.demo.controller;

import com.abn.amro.demo.dto.RecipeDTO;
import com.abn.amro.demo.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RecipeRestController {

    private final Logger logger = LoggerFactory.getLogger(RecipeRestController.class);

    @Autowired
    private RecipeService recipeService;

    /**
     * Endpoint receives a POST request, add the recipe and gives back the updated recipe.
     *
     * @return persisted entity
     */
    @PostMapping(value = "/add-recipe")
    public ResponseEntity<RecipeDTO> addRecipe(@Valid @RequestBody RecipeDTO recipeRequest) {
        logger.info("Request received to add the recipe with name: {}", recipeRequest.getName());
        return new ResponseEntity<>(recipeService.addRecipe(recipeRequest), HttpStatus.CREATED);
    }

    /**
     * Endpoint receives a PUT request, update the recipe details based on ID and gives back the updated recipe.
     *
     * @return updated recipe
     */
    @PutMapping(value = "/update-recipe")
    public ResponseEntity<RecipeDTO> updateRecipe(@Valid @RequestBody RecipeDTO recipeRequest) {
        logger.info("Request received to update the recipe with name and id: {} {}", recipeRequest.getName(), recipeRequest.getId());
        return new ResponseEntity<>(recipeService.updateRecipe(recipeRequest), HttpStatus.OK);
    }

    /**
     * Endpoint receives a GET request with recipe ID, processes it and gives back a recipe.
     *
     * @return return recipe based on recipe-id
     */
    @GetMapping(value = "/select-recipe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> selectRecipe(@PathVariable Long id) {
        logger.info("Request received to select the recipe id: {}", id);
        return new ResponseEntity<>(recipeService.selectRecipe(id), HttpStatus.OK);
    }

    /**
     * Endpoint receives a GET request, processes it and gives back a list of Recipes.
     *
     * @return all recipes
     */
    @GetMapping(value = "/select-all-recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecipeDTO>> selectAllRecipe() {
        logger.info("Request received to select the recipes");
        return new ResponseEntity<>(recipeService.selectRecipes(), HttpStatus.OK);
    }

    /**
     * Endpoint receives a DELETE request with recipe ID,
     * delete the recipe with given recipe ID and
     * returns the OK status.
     *
     * @return
     */
    @DeleteMapping(value = "/delete-recipe/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        logger.info("Request received to delete the recipe id: {}", id);
        recipeService.deleteRecipe(id);
        return "Recipe deleted with id: " + id;
    }

}
