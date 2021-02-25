package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.niveksys.recipeapp.command.IngredientCommand;
import com.niveksys.recipeapp.command.UnitOfMeasureCommand;
import com.niveksys.recipeapp.converter.IngredientCommandToIngredient;
import com.niveksys.recipeapp.converter.IngredientToIngredientCommand;
import com.niveksys.recipeapp.converter.UnitOfMeasureCommandToUnitOfMeasure;
import com.niveksys.recipeapp.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.niveksys.recipeapp.model.Ingredient;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.reactive.RecipeReactiveRepository;
import com.niveksys.recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTests {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

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
        this.ingredientService = new IngredientServiceImpl(this.recipeReactiveRepository,
                this.unitOfMeasureReactiveRepository, this.ingredientToIngredientCommand,
                this.ingredientCommandToIngredient);
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

        when(this.recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        // nwhen
        IngredientCommand returnCommand = this.ingredientService.findByRecipeIdAndIngredientId("1", "3").block();

        // the
        assertEquals("3", returnCommand.getId());
        verify(this.recipeReactiveRepository, times(1)).findById(anyString());
    }

    @Test
    public void saveIngredientCommand() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("3");
        ingredientCommand.setRecipeId("2");
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        ingredientCommand.getUom().setId("1234");

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(this.recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
        when(this.recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        // when
        IngredientCommand savedCommand = this.ingredientService.saveIngredientCommand(ingredientCommand).block();

        // then
        assertEquals("3", savedCommand.getId());
        verify(this.recipeReactiveRepository, times(1)).findById(anyString());
        verify(this.recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void deleteById() throws Exception {
        // given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);

        when(this.recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(this.recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

        // when
        this.ingredientService.deleteById("1", "3");

        // then
        verify(this.recipeReactiveRepository, times(1)).findById(anyString());
        verify(this.recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }
}
