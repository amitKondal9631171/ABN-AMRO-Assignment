/**
 * DTO to handle recipe ingredient list.
 */

package com.abn.amro.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class IngredientDTO {

    private Long id;
    @NotEmpty(message = "Ingredient name is required.")
    @Size(min = 1, max = 20, message = "Ingredient name must be less than 20")
    private String name;

}
