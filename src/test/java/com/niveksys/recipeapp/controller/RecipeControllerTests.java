package com.niveksys.recipeapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.exception.NotFoundException;
import com.niveksys.recipeapp.model.Notes;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.service.RecipeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTests {
    @MockBean
    RecipeService recipeService;

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Set<Recipe>> recipesCaptor;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void list() throws Exception {
        // given
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId("1");
        recipes.add(recipe1);
        Recipe recipe2 = new Recipe();
        recipe2.setId("2");
        recipes.add(recipe2);

        // when
        when(this.recipeService.getRecipes()).thenReturn(recipes);

        // then
        mockMvc.perform(get("/recipes")).andExpect(status().isOk()).andExpect(view().name("recipes/list"))
                .andExpect(model().attributeExists("recipes"));
    }

    @Test
    public void newRecipe() throws Exception {
        mockMvc.perform(get("/recipes/new")).andExpect(status().isOk()).andExpect(view().name("recipes/edit"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void createOrUpdate() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        // when
        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        // then
        mockMvc.perform(post("/recipes").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
                .param("description", "some string").param("directions", "some directions"))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipes/2"));
    }

    @Test
    public void createOrUpdateValidationFail() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        // when
        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        // then
        mockMvc.perform(post("/recipes").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
                .param("cookTime", "3000")).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipes/edit"));
    }

    @Test
    public void show() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Notes notes = new Notes();
        notes.setRecipeNotes("Notes");
        recipe.setNotes(notes);

        // when
        when(this.recipeService.findById(anyString())).thenReturn(recipe);

        // then
        this.mockMvc.perform(get("/recipes/1")).andExpect(status().isOk()).andExpect(view().name("recipes/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void showNotFoundException() throws Exception {
        // given
        when(this.recipeService.findById(anyString())).thenThrow(NotFoundException.class);

        // when
        mockMvc.perform(get("/recipes/1")).andExpect(status().isNotFound()).andExpect(view().name("404error"));
    }

    @Test
    public void edit() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        // when
        when(this.recipeService.findCommandById(anyString())).thenReturn(command);

        // then
        mockMvc.perform(get("/recipes/2/edit")).andExpect(status().isOk()).andExpect(view().name("recipes/edit"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void delete() throws Exception {
        // when & then
        mockMvc.perform(get("/recipes/1/delete")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes"));
        verify(recipeService, times(1)).deleteById(anyString());
    }
}