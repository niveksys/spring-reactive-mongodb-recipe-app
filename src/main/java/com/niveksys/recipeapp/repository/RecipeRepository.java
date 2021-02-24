package com.niveksys.recipeapp.repository;

import com.niveksys.recipeapp.model.Recipe;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {

}
