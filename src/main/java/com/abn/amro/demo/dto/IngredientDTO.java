/**
 * DTO to handle recipe ingredient list.
 */

package com.abn.amro.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientDTO {

    private Long id;
    private String name;

}
