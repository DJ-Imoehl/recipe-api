package com.imoehl.recipeapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.Set;

@Entity
public class Category {
    private @Id @GeneratedValue Long id;
    private CategoryType name;
    private String photo;
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<Recipe> recipes;

    public Category() {}

    public Category(CategoryType name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryType getName() {
        return name;
    }

    public void setName(CategoryType name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && name == category.name && Objects.equals(photo, category.photo)
                && Objects.equals(recipes, category.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photo, recipes);
    }
}
