package com.niveksys.recipeapp.repository.reactive;

import com.niveksys.recipeapp.model.Recipe;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
