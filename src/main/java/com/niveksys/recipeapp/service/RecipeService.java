package com.niveksys.recipeapp.service;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.model.Recipe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> findCommandById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<Void> deleteById(String id);
}
