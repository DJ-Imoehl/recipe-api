package com.imoehl.recipeapi.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
public class LoadDatabase {

    public static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private List<Recipe> readJsonData() {
        List<Recipe> recipes;
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("src\\main\\resources\\data.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonData);
            recipes = mapper.readValue(jsonNode.get("data").get("recipes").traverse(), new TypeReference<List<Recipe>>() {});
            log.info("Loaded " + recipes.size() + " items into recipe list.");
        } catch (Exception e) {
            log.error("FILE READ ERROR - ERROR IMPORTING DATA FROM JSON FILE");
            log.error(e.getMessage());
            recipes = new ArrayList<>();
        }
        return recipes;
    }


    @Bean
    CommandLineRunner initDatabase(RecipeRepository recipeRepository, CategoryRepository categoryRepository){
        List<Recipe> recipes = readJsonData();

        Category dinner = new Category(CategoryType.DINNER, null);
        Category breakfast = new Category(CategoryType.BREAKFAST, null);
        Category easy = new Category(CategoryType.QUICK, null);;

        return args -> {
            log.info("-------------Loading Default Data-------------");
            categoryRepository.saveAll(Arrays.asList(dinner, breakfast, easy));
            recipes.get(0).setCategories(Stream.of(breakfast, easy).collect(Collectors.toSet()));
            recipes.get(1).setCategories(Stream.of(dinner).collect(Collectors.toSet()));
            for(Recipe recipe : recipes) {
                log.info("Loading " + recipeRepository.save(recipe));
            }
            Set<Recipe> scrambledEggsSet = Stream.of(recipes.get(0)).collect(Collectors.toSet());
            Set<Recipe> chickenSet = Stream.of(recipes.get(1)).collect(Collectors.toSet());

            breakfast.setRecipes(scrambledEggsSet);
            easy.setRecipes(scrambledEggsSet);
            dinner.setRecipes(chickenSet);
            categoryRepository.saveAll(Arrays.asList(dinner, breakfast, easy));
        };
    }

}
