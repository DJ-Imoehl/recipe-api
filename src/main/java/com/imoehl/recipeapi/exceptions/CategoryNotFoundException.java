package com.imoehl.recipeapi.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id) {super("Could not find category with id: " + id);}
}
