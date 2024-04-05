package com.imoehl.recipeapi.models;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Objects;

@Entity
public class Recipe {

    private @Id @GeneratedValue Long id;
    private String name;
    private StringBuilder story;
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Ingredient> ingredientList;
    private List<String> directions;

    public Recipe() {}

    public Recipe(String name, StringBuilder story, List<Ingredient> ingredientList, List<String> directions) {
        this.name = name;
        this.story = story;
        this.ingredientList = ingredientList;
        this.directions = directions;
    }

    public Recipe(String name, List<Ingredient> ingredientList, List<String> directions) {
        this.name = name;
        this.story = new StringBuilder();
        this.ingredientList = ingredientList;
        this.directions = directions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StringBuilder getStory() {
        return story;
    }

    public void setStory(StringBuilder story) {
        this.story = story;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) && Objects.equals(name, recipe.name) && Objects.equals(story, recipe.story) && Objects.equals(ingredientList, recipe.ingredientList) && Objects.equals(directions, recipe.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, story, ingredientList, directions);
    }

    @Override
    public String toString() {
        return "Ingredient{ id=" + this.id + ", name=" + name + " }";
    }
}
