/**
 * Data  Object to receive client request details.
 */

package com.abn.amro.demo.dto;

import com.abn.amro.demo.entity.IngredientEntity;
import lombok.Data;

import java.util.List;

@Data
public class RequestDTO {
    private Long id;
    private String name;
    private List<IngredientEntity> ingredients;
    private String cookingInstructions;
    private String recipeType;
}
