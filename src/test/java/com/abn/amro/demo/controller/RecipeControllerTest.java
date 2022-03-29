package com.abn.amro.demo.controller;

import com.abn.amro.demo.dto.RequestDTO;
import com.abn.amro.demo.dto.ResponseDTO;
import com.abn.amro.demo.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@WithMockUser(username = "user", password = "password", roles = "USER")
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipeService service;
    @Autowired
    private ObjectMapper objectMapper;

    public String getRequestJson() throws IOException {
         return Files.readString(Path.of("./src/test/resources/request.json"));
      }

    public RequestDTO getRequestDTO() throws IOException {
        return objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/request.json"), RequestDTO.class);
    }

    public ResponseDTO getResponseDTO() throws IOException {
         return objectMapper.readValue(ResourceUtils.getFile("./src/test/resources/response.json"), ResponseDTO.class);
    }

    @Test
    public void testAdd() throws Exception {
        Mockito.when(service.addRecipe(getRequestDTO())).thenReturn(getResponseDTO());
        mockMvc.perform(
                post("/add-recipe")
                        /*.with(csrf())*/
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRequestJson())
        ).andExpect(status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        Mockito.when(service.addRecipe(getRequestDTO())).thenReturn(getResponseDTO());
        mockMvc.perform(
                put("/update-recipe")
                        /*.with(csrf())*/
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRequestJson())
        ).andExpect(status().isOk());
    }

    @Test
    public void testSelect() throws Exception {
        Mockito.when(service.selectRecipe(1L)).thenReturn(getResponseDTO());
        mockMvc.perform(get("/select-recipe/{id}", 1L)).andExpect(status().isOk());
    }

    @Test
    public void testSelectAll() throws Exception {
        Mockito.when(service.selectRecipes()).thenReturn(Stream.of(getResponseDTO()).collect(Collectors.toList()));
        mockMvc.perform(get("/select-recipes")).andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/delete-recipe/{id}", 1L)
                /*.with(csrf())*/
        ).andExpect(status().isNoContent());
    }
}
