package com.imoehl.recipeapi.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {super("Couldn't find Recipe with id: " + id);}
}
