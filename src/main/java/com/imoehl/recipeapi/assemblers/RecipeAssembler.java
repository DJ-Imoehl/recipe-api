package com.imoehl.recipeapi.assemblers;

import com.imoehl.recipeapi.controllers.RecipeController;
import com.imoehl.recipeapi.models.Recipe;
import com.imoehl.recipeapi.repositories.RecipeRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RecipeAssembler implements RepresentationModelAssembler<Recipe, EntityModel<Recipe>> {

    @Override
    public EntityModel<Recipe> toModel(Recipe entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(RecipeController.class).getRecipe(entity.getId())).withSelfRel(),
                linkTo(methodOn(RecipeController.class).all()).withRel("/recipes"));
    }
}
