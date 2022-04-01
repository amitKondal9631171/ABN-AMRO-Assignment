package com.abn.amro.demo.service;

import com.abn.amro.demo.dao.RecipeDao;
import com.abn.amro.demo.dto.RecipeDTO;
import com.abn.amro.demo.entity.RecipeEntity;
import com.abn.amro.demo.exceptions.RecipeProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeServiceTest {

    private static final String ERROR_MESSAGE = "Recipe not found";
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecipeDao recipeDao;
    @Autowired
    private RecipeService recipeService;
    @MockBean
    private ModelMapper modelMapper;

    RecipeDTO getRecipeDTO() throws IOException {
        return objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/request.json"), RecipeDTO.class);
    }

    RecipeEntity getRecipeEntity() throws IOException {
        RecipeEntity responseDTO = objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/response.json"), RecipeEntity.class);
        return objectMapper.convertValue(responseDTO, RecipeEntity.class);
    }

    @Test
    void testAdd() throws Exception {
        //Given
        RecipeDTO recipeDTO = getRecipeDTO();
        Mockito.when(modelMapper.map(recipeDTO, RecipeEntity.class)).thenReturn(getRecipeEntity());
        Mockito.when(modelMapper.map(getRecipeEntity(), RecipeDTO.class)).thenReturn(getRecipeDTO());
        Mockito.when(recipeDao.save(getRecipeEntity())).thenReturn(getRecipeEntity());
        //When
        RecipeDTO responseDTO = recipeService.addRecipe(getRecipeDTO());
        //Then
        assertEquals("Mushroom", responseDTO.getName());
    }

    @Test
    void testUpdate() throws Exception {
        //Given
        RecipeDTO recipeDTO = getRecipeDTO();

        Mockito.when(recipeDao.findById(recipeDTO.getId())).thenReturn(Optional.of(getRecipeEntity()));
        Mockito.when(modelMapper.map(getRecipeDTO(), RecipeEntity.class)).thenReturn(getRecipeEntity());
        Mockito.when(recipeDao.save(getRecipeEntity())).thenReturn(getRecipeEntity());
        Mockito.when(modelMapper.map(getRecipeEntity(), RecipeDTO.class)).thenReturn(getRecipeDTO());
        //When
        RecipeDTO responseDTO = recipeService.updateRecipe(getRecipeDTO());
        //Then
        assertEquals("Veg", responseDTO.getRecipeType());
    }

    @Test
    void testSelect() throws Exception {
        //Given
        Long recipeId = 1L;

        Mockito.when(recipeDao.findById(recipeId)).thenReturn(Optional.of(getRecipeEntity()));
        Mockito.when(modelMapper.map(getRecipeEntity(), RecipeDTO.class)).thenReturn(getRecipeDTO());
        //When
        RecipeDTO responseDTO = recipeService.selectRecipe(1L);
        //Then
        assertEquals(1, responseDTO.getId());
    }

    @Test
    void testSelectAll() throws Exception {
        List<RecipeEntity> entities = Stream.of(getRecipeEntity()).collect(Collectors.toList());
        //When
        Mockito.when(recipeDao.findAll()).thenReturn(entities);
        Mockito.when(modelMapper.map(entities, new TypeToken<List<RecipeDTO>>() {
        }.getType())).thenReturn(entities);
        List<RecipeEntity> responseDTO = recipeDao.findAll();
        //Then
        assertSame(entities, responseDTO);
    }

    @Test
    void testDelete() throws Exception {
        //Given
        Long recipeId = 1L;
        when(recipeDao.findById(recipeId)).thenReturn(Optional.of(getRecipeEntity()));
        //When
        recipeService.deleteRecipe(1L);
        //Then
        verify(recipeDao, times(1)).findById(1L);
    }

    @Test
    void testUpdateResourceNotFoundException() {
        RecipeProcessingException expectedException = new RecipeProcessingException("Recipe not found to update with id: 1", HttpStatus.NOT_FOUND);
        //Given
        Long recipeId = 1L;

        when(recipeDao.findById(recipeId)).thenThrow(expectedException);
        try {
            //When
            recipeService.updateRecipe(getRecipeDTO());
        } catch (IOException | RecipeProcessingException ex) {
            //Then
            assertSame(ex, expectedException);
        }
    }

    @Test
    void testSelectResourceNotFoundException() {
        RecipeProcessingException expectedException = new RecipeProcessingException("Recipe not found with id: 1", HttpStatus.NOT_FOUND);
        when(recipeDao.findById(1L)).thenThrow(expectedException);
        try {
            recipeService.selectRecipe(1L);
        } catch (RecipeProcessingException ex) {
            assertSame(ex, expectedException);
        }
    }

    @Test
    void testDeleteResourceNotFoundException() {
        RecipeProcessingException expectedException = new RecipeProcessingException("Recipe not found to select with id: 1", HttpStatus.NOT_FOUND);
        when(recipeDao.findById(1L)).thenThrow(expectedException);
        try {
            recipeService.deleteRecipe(1L);
        } catch (RecipeProcessingException ex) {
            assertSame(ex, expectedException);
        }
    }
}
