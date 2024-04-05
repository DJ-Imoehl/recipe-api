package com.imoehl.recipeapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Objects;
import java.util.Queue;

@Entity
public class Recipe {

    private @Id @GeneratedValue Long id;
    private String name;
    private StringBuilder story;
    private List<Ingredient> ingredientList;
    private Queue<String> directions;

    public Recipe() {}

    public Recipe(String name, StringBuilder story, List<Ingredient> ingredientList, Queue<String> directions) {
        this.name = name;
        this.story = story;
        this.ingredientList = ingredientList;
        this.directions = directions;
    }

    public Recipe(String name, List<Ingredient> ingredientList, Queue<String> directions) {
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

    public Queue<String> getDirections() {
        return directions;
    }

    public void setDirections(Queue<String> directions) {
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
}
