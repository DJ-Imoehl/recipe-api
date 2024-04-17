package com.imoehl.recipeapi.controllers;

import com.imoehl.recipeapi.assemblers.CategoryAssembler;
import com.imoehl.recipeapi.exceptions.CategoryNotFoundException;
import com.imoehl.recipeapi.models.Category;
import com.imoehl.recipeapi.repositories.CategoryRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin("http://localhost:3000/")
@RestController
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryAssembler categoryAssembler;

    public CategoryController(CategoryRepository categoryRepository, CategoryAssembler categoryAssembler) {
        this.categoryRepository = categoryRepository;
        this.categoryAssembler = categoryAssembler;
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
        Category recipe = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        return categoryAssembler.toModel(recipe);
    }
}
