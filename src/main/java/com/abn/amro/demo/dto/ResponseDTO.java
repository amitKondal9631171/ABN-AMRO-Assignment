package com.abn.amro.demo.dto;

import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
public class ResponseDTO{

    private Long id;
    private String name;
    private String cookingInstructions;
    private List<IngredientDTO> ingredients;
    private String recipeType;

    public static Comparator<ResponseDTO> compareByName = Comparator.comparing(e -> e.getName());

}
