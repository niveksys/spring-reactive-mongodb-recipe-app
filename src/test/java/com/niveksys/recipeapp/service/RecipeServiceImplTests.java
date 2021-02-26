package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.converter.RecipeCommandToRecipe;
import com.niveksys.recipeapp.converter.RecipeToRecipeCommand;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.reactive.RecipeReactiveRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTests {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void getRecipes() throws Exception {
        // given
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeSet = new HashSet<Recipe>();
        recipeSet.add(recipe);

        when(this.recipeReactiveRepository.findAll()).thenReturn(Flux.fromIterable(recipeSet));

        // when
        List<Recipe> returnRecipeSet = this.recipeService.getRecipes().collectList().block();

        // then
        assertEquals(1, returnRecipeSet.size());
        verify(this.recipeReactiveRepository, times(1)).findAll();
        verify(this.recipeReactiveRepository, never()).findById(anyString());
    }

    @Test
    public void findById() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(this.recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        // when
        Recipe returnRecipe = this.recipeService.findById("1").block();

        // then
        assertNotNull(returnRecipe, "Null recipe returned");
        verify(this.recipeReactiveRepository, times(1)).findById(anyString());
        verify(this.recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void findCommandById() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(this.recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        // when
        RecipeCommand returnCommand = this.recipeService.findCommandById("1").block();

        // then
        assertNotNull(returnCommand, "Null recipe returned");
        verify(this.recipeReactiveRepository, times(1)).findById(anyString());
        verify(this.recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void deleteById() throws Exception {
        // given
        String id = "2";
        when(this.recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());

        // when
        this.recipeService.deleteById(id);

        // then
        verify(this.recipeReactiveRepository, times(1)).deleteById(anyString());
    }
}
