package com.abn.amro.demo.service;

import com.abn.amro.demo.dao.RecipeDao;
import com.abn.amro.demo.dto.RequestDTO;
import com.abn.amro.demo.dto.ResponseDTO;
import com.abn.amro.demo.entity.IngredientEntity;
import com.abn.amro.demo.entity.RecipeEntity;
import com.abn.amro.demo.exceptions.RecipeProcessingException;
import com.abn.amro.demo.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeServiceTest {

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecipeDao recipeDao;
    @Autowired
    private RecipeService recipeService;
    @MockBean
    private ModelMapper modelMapper;

    public RequestDTO getRequestDTO() throws IOException {
        return objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/request.json"), RequestDTO.class);
    }

    public RecipeEntity getRecipeEntity() throws IOException {
        ResponseDTO responseDTO = objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/response.json"), ResponseDTO.class);
        return objectMapper.convertValue(responseDTO, RecipeEntity.class);
    }

    public ResponseDTO getResponseDTO() throws IOException {
        return objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/response.json"), ResponseDTO.class);
    }

    @Test
    public void testAdd() throws Exception {
        Mockito.when(modelMapper.map(getRequestDTO(), RecipeEntity.class)).thenReturn(getRecipeEntity());
        Mockito.when(modelMapper.map(getRecipeEntity(), ResponseDTO.class)).thenReturn(getResponseDTO());
        Mockito.when(recipeDao.save(getRecipeEntity())).thenReturn(getRecipeEntity());
        ResponseDTO responseDTO = recipeService.addRecipe(getRequestDTO());
        assertTrue(responseDTO.getName().equals("Vikas"));
    }

    @Test
    public void testUpdate() throws Exception {
        assertNotNull(recipeDao);
        Mockito.when(recipeDao.findById(getRequestDTO().getId())).thenReturn(Optional.of(getRecipeEntity()));
        Mockito.when(recipeDao.save(getRecipeEntity())).thenReturn(getRecipeEntity());
        Mockito.when(modelMapper.map(getRecipeEntity(), ResponseDTO.class)).thenReturn(getResponseDTO());
        ResponseDTO responseDTO = recipeService.updateRecipe(getRequestDTO());
        assertTrue(responseDTO.getRecipeType().equals("NonVeg"));
    }

    @Test
    public void testSelect() throws Exception {
        Mockito.when(recipeDao.findById(1L)).thenReturn(Optional.of(getRecipeEntity()));
        Mockito.when(modelMapper.map(getRecipeEntity(), ResponseDTO.class)).thenReturn(getResponseDTO());
        ResponseDTO responseDTO = recipeService.selectRecipe(1L);
        assertTrue(responseDTO.getId().equals(1L));
    }

    @Test
    public void testSelectAll() throws Exception {
        List<RecipeEntity> entities = Stream.of(getRecipeEntity()).collect(Collectors.toList());
        Mockito.when(recipeDao.findAll()).thenReturn(entities);
        Mockito.when(modelMapper.map(entities, new TypeToken<List<ResponseDTO>>() {}.getType())).thenReturn(entities);
        List<RecipeEntity> responseDTO = recipeDao.findAll();
    }

    @Test
    public void testDelete() throws Exception {
        when(recipeDao.findById(1L)).thenReturn(Optional.of(getRecipeEntity()));
        recipeService.deleteRecipe(1L);
        verify(recipeDao, times(1)).findById(1L);
    }

    @Test
    public void testUpdateResourceNotFoundException() {
        when(recipeDao.findById(1L)).thenThrow(new RecipeProcessingException("Resource not exist", HttpStatus.NOT_FOUND) );
        RecipeProcessingException thrown = assertThrows(
                RecipeProcessingException.class,
                () -> recipeService.updateRecipe(getRequestDTO()),
                "Resource not exist"
        );

        assertTrue(thrown.getHttpStatus().value() == HttpStatus.NOT_FOUND.value());
    }
    @Test
    public void testSelectResourceNotFoundException()  {
        when(recipeDao.findById(1L)).thenThrow(new RecipeProcessingException("Resource not exist", HttpStatus.NOT_FOUND) );
        RecipeProcessingException thrown = assertThrows(
                RecipeProcessingException.class,
                () -> recipeService.selectRecipe(1L),
                "Resource not exist"
        );

        assertTrue(thrown.getHttpStatus().value() == HttpStatus.NOT_FOUND.value());
    }
    @Test
    public void testDeleteResourceNotFoundException()  {

        when(recipeDao.findById(1L)).thenThrow(new RecipeProcessingException("Resource not exist", HttpStatus.NOT_FOUND) );
        RecipeProcessingException thrown = assertThrows(
                RecipeProcessingException.class,
                () -> recipeService.deleteRecipe(1L),
                "Resource not exist"
        );

        assertTrue(thrown.getHttpStatus().value() == HttpStatus.NOT_FOUND.value());
    }
}
