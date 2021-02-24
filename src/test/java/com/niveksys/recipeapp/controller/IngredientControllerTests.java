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

import com.niveksys.recipeapp.command.IngredientCommand;
import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.command.UnitOfMeasureCommand;
import com.niveksys.recipeapp.service.IngredientService;
import com.niveksys.recipeapp.service.RecipeService;
import com.niveksys.recipeapp.service.UnitOfMeasureService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTests {

    @MockBean
    RecipeService recipeService;

    @MockBean
    UnitOfMeasureService unitOfMeasureService;

    @MockBean
    IngredientService ingredientService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void list() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        when(this.recipeService.findCommandById(anyString())).thenReturn(command);

        // when
        this.mockMvc.perform(get("/recipes/1/ingredients")).andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/list")).andExpect(model().attributeExists("recipe"));

        // then
        verify(this.recipeService, times(1)).findCommandById(anyString());
    }

    @Test
    public void newIngredient() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId("1");

        // when
        when(this.recipeService.findCommandById(anyString())).thenReturn(command);
        when(this.unitOfMeasureService.getUomCommands()).thenReturn(new HashSet<>());

        // then
        mockMvc.perform(get("/recipes/1/ingredients/new")).andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/edit")).andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uoms"));

        verify(recipeService, times(1)).findCommandById(anyString());
    }

    @Test
    public void createOrUpdate() throws Exception {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");

        // when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        // then
        mockMvc.perform(post("/recipes/2/ingredients").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "").param("description", "some string")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/2/ingredients/3"));
    }

    @Test
    public void show() throws Exception {
        // given
        IngredientCommand command = new IngredientCommand();
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        command.setUom(uomCommand);
        when(this.ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(command);

        // when
        this.mockMvc.perform(get("/recipes/1/ingredients/2")).andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/show")).andExpect(model().attributeExists("ingredient"));

        // then
        verify(this.ingredientService, times(1)).findByRecipeIdAndIngredientId(anyString(), anyString());
    }

    @Test
    public void edit() throws Exception {

        // given
        IngredientCommand command = new IngredientCommand();

        // when
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(command);
        when(unitOfMeasureService.getUomCommands()).thenReturn(new HashSet<>());

        // then
        mockMvc.perform(get("/recipes/1/ingredients/2/edit")).andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/edit")).andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uoms"));
    }

    @Test
    public void delete() throws Exception {

        // then
        mockMvc.perform(get("/recipes/2/ingredients/3/delete")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/2/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyString(), anyString());

    }
}
