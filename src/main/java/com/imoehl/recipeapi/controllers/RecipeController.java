package com.imoehl.recipeapi.controllers;

import com.imoehl.recipeapi.assemblers.RecipeAssembler;
import com.imoehl.recipeapi.exceptions.RecipeNotFoundException;
import com.imoehl.recipeapi.models.Recipe;
import com.imoehl.recipeapi.repositories.RecipeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:3000/")
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

    @PutMapping("/recipes/{id}")
    public ResponseEntity<?> createEditRecipe(@PathVariable Long id, @RequestBody Recipe newRecipe) {
        //Override recipe data or create new recipe
        Recipe modifiedRecipe = recipeRepository
                .findById(id)
                .map(recipe -> {
                    if(newRecipe.getName() != null) {
                        recipe.setName(newRecipe.getName());
                    }
                    if(newRecipe.getStory() != null) {
                        recipe.setStory(newRecipe.getStory());
                    }
                    if(newRecipe.getDirections() != null) {
                        recipe.setDirections(newRecipe.getDirections());
                    }
                    if(newRecipe.getIngredientList() != null) {
                        recipe.setIngredientList(newRecipe.getIngredientList());
                    }
                    return recipeRepository.save(recipe);
                }).orElseGet(() -> {
                    newRecipe.setId(id);
                    return recipeRepository.save(newRecipe);
                });
        EntityModel<Recipe> model = recipeAssembler.toModel(modifiedRecipe);
        return ResponseEntity
                .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        recipeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
