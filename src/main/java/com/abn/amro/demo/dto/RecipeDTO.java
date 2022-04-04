/**
 * Data  Object to receive client request details.
 */

package com.abn.amro.demo.dto;

import com.abn.amro.demo.entity.IngredientEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Data
public class RecipeDTO {

    private Long id;
    @NotNull(message = "Recipe Name is required")
    @Size(min = 1, max = 50, message = "Recipe name must be of length 1 to 50")
    private String name;
    @NotNull(message = "Recipe Ingredients required")
    private List<IngredientEntity> ingredients;
    @NotNull(message = "Recipe Cooking instructions are required")
    @Size(min = 1, max = 200, message = "Recipe Cooking instructions must be of length 1 to 200")
    private String cookingInstructions;
    @NotNull(message = "Recipe type is required.")
    @Size(min = 1, max = 10, message = "Recipe type must be of length 1 to 10")
    private String recipeType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd‐MM‐yyyy HH:mm")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd‐MM‐yyyy HH:mm")
    private Date updatedAt;
}
