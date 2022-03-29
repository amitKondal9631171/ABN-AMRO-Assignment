package com.abn.amro.demo.dao;

import com.abn.amro.demo.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeDao extends JpaRepository<RecipeEntity, Long> {
}
