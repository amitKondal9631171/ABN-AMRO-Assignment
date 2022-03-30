/**
 * Data  Object to send response to client.
 */

package com.abn.amro.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {

    private Long id;
    private String name;
    private String cookingInstructions;
    private List<IngredientDTO> ingredients;
    private String recipeType;

}
