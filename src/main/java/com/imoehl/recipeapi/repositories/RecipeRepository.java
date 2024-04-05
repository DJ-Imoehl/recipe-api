package com.imoehl.recipeapi.repositories;

import com.imoehl.recipeapi.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
