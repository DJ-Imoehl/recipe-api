package com.imoehl.recipeapi.controllers;

import com.fasterxml.jackson.databind.ser.std.IterableSerializer;
import com.imoehl.recipeapi.assemblers.CategoryAssembler;
import com.imoehl.recipeapi.assemblers.RecipeAssembler;
import com.imoehl.recipeapi.exceptions.CategoryNotFoundException;
import com.imoehl.recipeapi.models.Category;
import com.imoehl.recipeapi.models.Recipe;
import com.imoehl.recipeapi.repositories.CategoryRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin("http://localhost:3000/")
@RestController
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryAssembler categoryAssembler;
    private final RecipeAssembler recipeAssembler;

    public CategoryController(CategoryRepository categoryRepository, CategoryAssembler categoryAssembler, RecipeAssembler recipeAssembler) {
        this.categoryRepository = categoryRepository;
        this.categoryAssembler = categoryAssembler;
        this.recipeAssembler = recipeAssembler;
    }

    @GetMapping("/categories")
    public CollectionModel<EntityModel<Category>> all() {
        List<EntityModel<Category>> categories = categoryRepository.findAll().stream()
                .map(categoryAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(categories, linkTo(methodOn(CategoryController.class).all()).withSelfRel());
    }

    @GetMapping("/categories/{id}")
    public EntityModel<Category> getCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        return categoryAssembler.toModel(category);
    }

    @GetMapping("/categories/{id}/recipes")
    public CollectionModel<EntityModel<Recipe>> getCategoryRecipes(@PathVariable Long id) {
        List<EntityModel<Recipe>> recipes = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id))
                .getRecipes().stream().map(recipeAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(recipes, linkTo(methodOn(CategoryController.class).getCategoryRecipes(id)).withRel("/categories/" + id + "/recipes"));
    }

    @GetMapping("/categories/featured")
    public CollectionModel<EntityModel<Category>> getFeaturedCategories() {
        List<Category> featuredCategories =
                new ArrayList<>(categoryRepository.findAllById(Arrays.asList(1L, 2L, 3L, 4L)));

        List<EntityModel<Category>> categories = featuredCategories.stream()
                .map(categoryAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(categories, linkTo(methodOn(CategoryController.class).all()).withSelfRel());
    }
}
