package com.abn.amro.demo.dao;

import com.abn.amro.demo.dto.RequestDTO;
import com.abn.amro.demo.dto.ResponseDTO;
import com.abn.amro.demo.entity.RecipeEntity;
import com.abn.amro.demo.exceptions.RecipeProcessingException;
import com.abn.amro.demo.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeDaoTest {

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecipeDao recipeDao;
    @Autowired
    private RecipeService recipeService;
    @MockBean
    private ModelMapper modelMapper;

    private RequestDTO getRequestDTO() throws IOException {
        return objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/request.json"), RequestDTO.class);
    }

    @Test
    void testAdd() throws Exception {
        RequestDTO entity = getRequestDTO();
        entity.setName("MushroomMushroomMushroomMushroomMushroomMushroomMushroomMushroomMushroomMushroomMushroomMushroomMushroom");
        DataIntegrityViolationException expectedException = new DataIntegrityViolationException("Value too long for column");
         try{
            recipeService.addRecipe(entity);
        }catch (DataIntegrityViolationException ex){
            assertSame(ex, expectedException);
            assertTrue(ex.getCause().getCause().getMessage().contains(expectedException.getCause().getCause().getMessage()));
        }
    }


}

