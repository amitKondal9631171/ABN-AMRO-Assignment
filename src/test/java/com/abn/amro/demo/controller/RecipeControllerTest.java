package com.abn.amro.demo.controller;

import com.abn.amro.demo.dto.RecipeDTO;
import com.abn.amro.demo.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", password = "password", roles = "USER")
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipeService service;
    @Autowired
    private ObjectMapper objectMapper;

    String getRequestJson() throws IOException {
        return Files.readString(Path.of("./src/test/resources/request.json"));
    }

    RecipeDTO getRecipeDTO() throws IOException {
        return objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/request.json"), RecipeDTO.class);
    }

    @Test
    void addRecipeTest() throws Exception {
        //Given
        RecipeDTO recipeDTO = getRecipeDTO();
        //When
        Mockito.when(service.addRecipe(recipeDTO)).thenReturn(getRecipeDTO());
        ResultActions resultActions = mockMvc.perform(
                post("/add-recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRequestJson())
        );
        //Then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    void updateRecipeTest() throws Exception {
        //Given
        RecipeDTO recipeDTO = getRecipeDTO();
        //When
        Mockito.when(service.addRecipe(recipeDTO)).thenReturn(getRecipeDTO());
        ResultActions resultActions = mockMvc.perform(
                put("/update-recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRequestJson())
        );
        //Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void selectRecipeTest() throws Exception {
        //Given
        Long recipeId = 1L;
        //When
        Mockito.when(service.selectRecipe(recipeId)).thenReturn(getRecipeDTO());
        ResultActions resultActions = mockMvc.perform(get("/select-recipe/{id}", 1L));
        //Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void selectListOfRecipesTest() throws Exception {
        //When
        Mockito.when(service.selectRecipes()).thenReturn(Stream.of(getRecipeDTO()).collect(Collectors.toList()));
        ResultActions resultActions = mockMvc.perform(get("/select-all-recipe"));
        //Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void deleteRecipeTest() throws Exception {
        //Given
        Long recipeId = 1L;
        //When
        ResultActions resultActions = mockMvc.perform(delete("/delete-recipe/{id}", recipeId)
        );
        //Then
        resultActions.andExpect(status().isOk());
    }
}
