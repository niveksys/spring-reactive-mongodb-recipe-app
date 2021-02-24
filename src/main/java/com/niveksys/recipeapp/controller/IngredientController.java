package com.niveksys.recipeapp.controller;

import com.niveksys.recipeapp.command.IngredientCommand;
import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.command.UnitOfMeasureCommand;
import com.niveksys.recipeapp.service.IngredientService;
import com.niveksys.recipeapp.service.RecipeService;
import com.niveksys.recipeapp.service.UnitOfMeasureService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
            UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    public String list(@PathVariable String recipeId, Model model) {
        log.debug("LIST all ingredients.");
        model.addAttribute("recipe", this.recipeService.findCommandById(recipeId));
        return "recipes/ingredients/list";
    }

    @GetMapping("/recipes/{recipeId}/ingredients/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {
        log.debug("NEW ingredient form.");

        RecipeCommand recipeCommand = this.recipeService.findCommandById(recipeId);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", this.unitOfMeasureService.getUomCommands());
        return "recipes/ingredients/edit";
    }

    @PostMapping("/recipes/{recipeId}/ingredients")
    public String createOrUpdate(@ModelAttribute IngredientCommand command) {
        log.debug("CREATE a new ingredient, or UPDATE a specific ingredient, then redirect to SHOW.");
        IngredientCommand savedCommand = this.ingredientService.saveIngredientCommand(command);

        log.debug("Saved Receipe ID:" + savedCommand.getRecipeId());
        log.debug("Saved Ingredient ID:" + savedCommand.getId());

        return "redirect:/recipes/" + savedCommand.getRecipeId() + "/ingredients/" + savedCommand.getId();
    }

    @GetMapping("/recipes/{recipeId}/ingredients/{id}")
    public String show(@PathVariable String recipeId, @PathVariable String id, Model model) {
        log.debug("SHOW info about a specific recipe.");
        model.addAttribute("ingredient", this.ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
        return "recipes/ingredients/show";
    }

    @GetMapping("/recipes/{recipeId}/ingredients/{id}/edit")
    public String edit(@PathVariable String recipeId, @PathVariable String id, Model model) {
        log.debug("EDIT form for a specific ingredient.");
        model.addAttribute("ingredient", this.ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
        model.addAttribute("uoms", this.unitOfMeasureService.getUomCommands());
        return "recipes/ingredients/edit";
    }

    @GetMapping("/recipes/{recipeId}/ingredients/{id}/delete")
    public String delete(@PathVariable String recipeId, @PathVariable String id) {
        log.debug("DELETE a specific ingredient, then redirect to LIST.");
        this.ingredientService.deleteById(recipeId, id);
        return "redirect:/recipes/" + recipeId + "/ingredients";
    }
}
