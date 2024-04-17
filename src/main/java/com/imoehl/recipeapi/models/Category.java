package com.imoehl.recipeapi.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Category {
    private @Id @GeneratedValue Long id;
    private CategoryType name;
    private String photo;

    private Long recipeId;

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

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && name == category.name && Objects.equals(photo, category.photo)
                && Objects.equals(recipeId, category.recipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photo, recipeId);
    }
}
