package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.niveksys.recipeapp.command.IngredientCommand;
import com.niveksys.recipeapp.converter.IngredientCommandToIngredient;
import com.niveksys.recipeapp.converter.IngredientToIngredientCommand;
import com.niveksys.recipeapp.converter.UnitOfMeasureCommandToUnitOfMeasure;
import com.niveksys.recipeapp.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.niveksys.recipeapp.model.Ingredient;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.RecipeRepository;
import com.niveksys.recipeapp.repository.UnitOfMeasureRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTests {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    IngredientService ingredientService;

    public IngredientServiceImplTests() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
                new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(
                new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    public void setUp() {
        this.ingredientService = new IngredientServiceImpl(this.recipeRepository, this.unitOfMeasureRepository,
                this.ingredientToIngredientCommand, this.ingredientCommandToIngredient);
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(this.recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        // nwhen
        IngredientCommand returnCommand = this.ingredientService.findByRecipeIdAndIngredientId("1", "3");

        // the
        assertEquals("3", returnCommand.getId());
        verify(this.recipeRepository, times(1)).findById(anyString());
    }

    @Test
    public void saveIngredientCommand() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("3");
        ingredientCommand.setRecipeId("2");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(this.recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(this.recipeRepository.save(any())).thenReturn(savedRecipe);

        // when
        IngredientCommand savedCommand = this.ingredientService.saveIngredientCommand(ingredientCommand);

        // then
        assertEquals("3", savedCommand.getId());
        verify(this.recipeRepository, times(1)).findById(anyString());
        verify(this.recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void deleteById() throws Exception {
        // given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(this.recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        // when
        this.ingredientService.deleteById("1", "3");

        // then
        verify(this.recipeRepository, times(1)).findById(anyString());
        verify(this.recipeRepository, times(1)).save(any(Recipe.class));
    }
}
