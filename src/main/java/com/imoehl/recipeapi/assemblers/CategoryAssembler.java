package com.imoehl.recipeapi.assemblers;

import com.imoehl.recipeapi.controllers.CategoryController;
import com.imoehl.recipeapi.models.Category;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {


    @Override
    public EntityModel<Category> toModel(Category entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CategoryController.class).getCategory(entity.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).all()).withRel("/categories"));
    }
}
