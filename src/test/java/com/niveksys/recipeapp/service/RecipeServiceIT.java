package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.converter.RecipeCommandToRecipe;
import com.niveksys.recipeapp.converter.RecipeToRecipeCommand;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.RecipeRepository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceIT {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void saveRecipeCommand() throws Exception {
        // given
        Iterable<Recipe> recipes = this.recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipeCommand command = this.recipeToRecipeCommand.convert(recipe);

        // when
        command.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(command);

        // then
        assertEquals(NEW_DESCRIPTION, savedCommand.getDescription());
        assertEquals(recipe.getId(), savedCommand.getId());
        assertEquals(recipe.getCategories().size(), savedCommand.getCategories().size());
        assertEquals(recipe.getIngredients().size(), savedCommand.getIngredients().size());
    }
}
