package com.imoehl.recipeapi.config;

import com.imoehl.recipeapi.models.Category;
import com.imoehl.recipeapi.models.CategoryType;
import com.imoehl.recipeapi.models.Ingredient;
import com.imoehl.recipeapi.models.Recipe;
import com.imoehl.recipeapi.repositories.CategoryRepository;
import com.imoehl.recipeapi.repositories.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;


@Configuration
public class LoadDatabase {

    public static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RecipeRepository recipeRepository, CategoryRepository categoryRepository){
        List<String> seDirections = Arrays.asList(
                "Preheat pan to medium heat. Grease Pan.",
                "Scramble Eggs in a bowl with a fork or whisk. Season with salt and pepper.",
                "Pour eggs into pan. Cook until eggs solidify.",
                "Turn off the heat and add optional cheese. Enjoy!");
        Recipe scrambledEggs = new Recipe(
                "Scrambled Eggs",
                Arrays.asList(
                        new Ingredient("Eggs", false),
                        new Ingredient("Salt", false),
                        new Ingredient("Pepper", true),
                        new Ingredient("Cheese", true)),
                seDirections,
                null);

        List<String> gcpDirections = Arrays.asList(
                "Preheat oven to 425. Grease a half sheet pan.",
                "Cut potatoes into quarters. Place in bowl with chicken thighs.",
                "Add olive oil, lemon zest, lemon juice, garlic, and thyme to bowl. Mix well.",
                "Put chicken and potatoes onto sheet pan, spread out for even cooking.",
                "Bake for 45 minutes, checking the oven periodically.",
                "Once chicken thighs hit 165, take out of oven. Let cool for 5 minutes and enjoy!");
        Recipe greekChickenPotatoes = new Recipe(
                "Greek Chicken and Potatoes",
                Arrays.asList(
                        new Ingredient("Chicken Thighs", "lbs", 1.5, false),
                        new Ingredient("Russet Potatoes", "count", 2, false),
                        new Ingredient("Olive Oil", "Tbsp", 4, false),
                        new Ingredient("Garlic", "cloves", 5, false),
                        new Ingredient("Thyme", "tsp", 2, true)),
                gcpDirections,
                null);
        Category dinner = new Category(CategoryType.DINNER, null);
        Category breakfast = new Category(CategoryType.BREAKFAST, null);
        Category easy = new Category(CategoryType.QUICK, null);;

        //scrambledEggs.setCategorylist(Arrays.asList(breakfast, easy));
        //greekChickenPotatoes.setCategorylist(Arrays.asList(dinner));

        return args -> {
            log.info("-------------Loading Default Data-------------");
            log.info("Loading " + recipeRepository.save(scrambledEggs));
            log.info("Loading " + recipeRepository.save(greekChickenPotatoes));
            breakfast.setRecipeId(scrambledEggs.getId());
            easy.setRecipeId(scrambledEggs.getId());
            dinner.setRecipeId(greekChickenPotatoes.getId());
            categoryRepository.saveAll(Arrays.asList(dinner, breakfast, easy));
        };
    }

}
