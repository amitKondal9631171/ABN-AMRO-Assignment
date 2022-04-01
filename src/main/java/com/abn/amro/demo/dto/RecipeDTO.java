/**
 * Data  Object to receive client request details.
 */

package com.abn.amro.demo.dto;

import com.abn.amro.demo.entity.IngredientEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Data
public class RecipeDTO {

    private Long id;
    @NotEmpty(message = "Recipe Name is required")
    @Size(min = 1, max = 50, message = "Recipe name must be less than 50")
    private String name;
    @NotEmpty(message = "Recipe Ingredients required")
    private List<IngredientEntity> ingredients;
    @NotEmpty(message = "Recipe Cooking instructions are required")
    @Size(min = 1, max = 1000, message = "Recipe Cooking instructions must be less than 1000")
    private String cookingInstructions;
    @NotEmpty(message = "Recipe type is required.")
    @Size(min = 1, max = 20, message = "Recipe type must be less than 20")
    private String recipeType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd‐MM‐yyyy HH:mm")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd‐MM‐yyyy HH:mm")
    private Date updatedAt;
}
