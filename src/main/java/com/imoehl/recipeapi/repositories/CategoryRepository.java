package com.imoehl.recipeapi.repositories;

import com.imoehl.recipeapi.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
