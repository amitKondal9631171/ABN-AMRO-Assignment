package com.abn.amro.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_recipe")
@Data
public class RecipeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_name", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "cooking_instructions", nullable = false)
    private String cookingInstructions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = IngredientEntity.class)
    @JoinColumn(name = "ri_fid", referencedColumnName = "id", nullable = false)
    private Set<IngredientEntity> ingredients;

    @Column(name = "recipe_type", length = 20, nullable = false)
    private String recipeType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd‐MM‐yyyy HH:mm")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd‐MM‐yyyy HH:mm")
    private Date updatedAt;

}
