package com.abn.amro.demo.controller;

import com.abn.amro.demo.dto.RequestDTO;
import com.abn.amro.demo.dto.ResponseDTO;
import com.abn.amro.demo.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeRestController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/add-recipe")
    public ResponseEntity<ResponseDTO> add(@RequestBody RequestDTO recipeRequest) {
        return new ResponseEntity<>(recipeService.addRecipe(recipeRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update-recipe")
    public ResponseEntity<ResponseDTO> update(@RequestBody RequestDTO recipeRequest) {
        return new ResponseEntity<>(recipeService.updateRecipe(recipeRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/select-recipe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getRecipe(@PathVariable Long id) {
        return new ResponseEntity<>(recipeService.selectRecipe(id), HttpStatus.OK);
    }

    @GetMapping(value = "/select-recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseDTO>> getRecipe() {
        return new ResponseEntity<>(recipeService.selectRecipes(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-recipe/{id}")
    public ResponseEntity<HttpStatus> deleteRecipes(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
