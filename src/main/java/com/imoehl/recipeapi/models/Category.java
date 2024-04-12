package com.imoehl.recipeapi.models;

import java.util.Objects;

public class Category {
    private CategoryType name;
    private String photo;

    public Category(CategoryType name, String photo) {
        this.name = name;
        this.photo = photo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name == category.name && Objects.equals(photo, category.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, photo);
    }
}
