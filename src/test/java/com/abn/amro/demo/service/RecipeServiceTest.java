package com.abn.amro.demo.service;

import com.abn.amro.demo.dao.RecipeDao;
import com.abn.amro.demo.dto.RequestDTO;
import com.abn.amro.demo.dto.ResponseDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeServiceTest {

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecipeDao recipeDao;
    @Autowired
    private RecipeService recipeService;
    @MockBean
    private ModelMapper modelMapper;

    private static final String ERROR_MESSAGE = "Recipe not found";

    RequestDTO getRequestDTO() throws IOException {
        return objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/request.json"), RequestDTO.class);
    }

    RecipeEntity getRecipeEntity() throws IOException {
        ResponseDTO responseDTO = objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/response.json"), ResponseDTO.class);
        return objectMapper.convertValue(responseDTO, RecipeEntity.class);
    }

    ResponseDTO getResponseDTO() throws IOException {
        return objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/response.json"), ResponseDTO.class);
    }

    @Test
    void testAdd() throws Exception {
        Mockito.when(modelMapper.map(getRequestDTO(), RecipeEntity.class)).thenReturn(getRecipeEntity());
        Mockito.when(modelMapper.map(getRecipeEntity(), ResponseDTO.class)).thenReturn(getResponseDTO());
        Mockito.when(recipeDao.save(getRecipeEntity())).thenReturn(getRecipeEntity());
        ResponseDTO responseDTO = recipeService.addRecipe(getRequestDTO());
        assertEquals("Mushroom", responseDTO.getName());
    }

    @Test
    void testUpdate() throws Exception {
        assertNotNull(recipeDao);
        Mockito.when(recipeDao.findById(getRequestDTO().getId())).thenReturn(Optional.of(getRecipeEntity()));
        Mockito.when(modelMapper.map(getRequestDTO(), RecipeEntity.class)).thenReturn(getRecipeEntity());
        Mockito.when(recipeDao.save(getRecipeEntity())).thenReturn(getRecipeEntity());
        Mockito.when(modelMapper.map(getRecipeEntity(), ResponseDTO.class)).thenReturn(getResponseDTO());
        ResponseDTO responseDTO = recipeService.updateRecipe(getRequestDTO());
        assertEquals("Veg", responseDTO.getRecipeType() );
    }

    @Test
    void testSelect() throws Exception {
        Mockito.when(recipeDao.findById(1L)).thenReturn(Optional.of(getRecipeEntity()));
        Mockito.when(modelMapper.map(getRecipeEntity(), ResponseDTO.class)).thenReturn(getResponseDTO());
        ResponseDTO responseDTO = recipeService.selectRecipe(1L);
        assertEquals(1, responseDTO.getId());
    }

    @Test
    void testSelectAll() throws Exception {
        List<RecipeEntity> entities = Stream.of(getRecipeEntity()).collect(Collectors.toList());
        Mockito.when(recipeDao.findAll()).thenReturn(entities);
        Mockito.when(modelMapper.map(entities, new TypeToken<List<ResponseDTO>>() {
        }.getType())).thenReturn(entities);
        List<RecipeEntity> responseDTO = recipeDao.findAll();
        assertSame(entities, responseDTO);
    }

    @Test
    void testDelete() throws Exception {
        when(recipeDao.findById(1L)).thenReturn(Optional.of(getRecipeEntity()));
        recipeService.deleteRecipe(1L);
        verify(recipeDao, times(1)).findById(1L);
    }

    @Test
    void testUpdateResourceNotFoundException() {
        RecipeProcessingException expectedException = new RecipeProcessingException("Recipe not found to update with id: 1", HttpStatus.NOT_FOUND);
        when(recipeDao.findById(1L)).thenThrow(expectedException);
        try{
            recipeService.updateRecipe(getRequestDTO());
        }catch (IOException | RecipeProcessingException ex){
            assertSame(ex, expectedException);
        }
    }

    @Test
    void testSelectResourceNotFoundException() {
        RecipeProcessingException expectedException = new RecipeProcessingException("Recipe not found with id: 1", HttpStatus.NOT_FOUND);
        when(recipeDao.findById(1L)).thenThrow(expectedException);
        try{
            recipeService.selectRecipe(1L);
        }catch (RecipeProcessingException ex){
            assertSame(ex, expectedException);
        }
    }

    @Test
    void testDeleteResourceNotFoundException() {
        RecipeProcessingException expectedException = new RecipeProcessingException("Recipe not found to select with id: 1", HttpStatus.NOT_FOUND);
        when(recipeDao.findById(1L)).thenThrow(expectedException);
        try{
            recipeService.deleteRecipe(1L);
        }catch (RecipeProcessingException ex){
            assertSame(ex, expectedException);
        }
    }
}
