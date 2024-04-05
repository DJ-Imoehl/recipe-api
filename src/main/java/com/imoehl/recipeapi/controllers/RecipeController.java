package com.imoehl.recipeapi.controllers;

import com.imoehl.recipeapi.assemblers.RecipeAssembler;
import com.imoehl.recipeapi.exceptions.RecipeNotFoundException;
import com.imoehl.recipeapi.models.Recipe;
import com.imoehl.recipeapi.repositories.RecipeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final RecipeAssembler recipeAssembler;

    public RecipeController(RecipeRepository recipeRepository, RecipeAssembler recipeAssembler) {
        this.recipeRepository = recipeRepository;
        this.recipeAssembler = recipeAssembler;
    }

    @GetMapping("/recipes")
    public CollectionModel<EntityModel<Recipe>> all() {
        List<EntityModel<Recipe>> recipes = recipeRepository.findAll().stream()
                .map(recipeAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(recipes, linkTo(methodOn(RecipeController.class).all()).withSelfRel());
    }

    @GetMapping("/recipes/{id}")
    public EntityModel<Recipe> getRecipe(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));

        return recipeAssembler.toModel(recipe);
    }

    @GetMapping("/recipes/random")
    public EntityModel<Recipe> getRandomRecipe() {
        if(recipeRepository.count() <= 0) {return null;}
        Random rn = new Random();
        int index = rn.nextInt(Math.toIntExact(recipeRepository.count())); // Our length will never be long enough for this to be a problem

        Recipe recipe = recipeRepository.findAll().get(index);
        return recipeAssembler.toModel(recipe);
    }
}
