package com.niveksys.recipeapp.controller;

import javax.validation.Valid;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.exception.NotFoundException;
import com.niveksys.recipeapp.service.RecipeService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private static final String RECIPE_CREATE_OR_UPDATE_VIEW = "recipes/edit";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({ "", "/" })
    public String list(Model model) {
        log.debug("LIST all recipes.");
        model.addAttribute("recipes", this.recipeService.getRecipes());
        return "recipes/list";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        log.debug("NEW recipe form.");
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_CREATE_OR_UPDATE_VIEW;
    }

    @PostMapping({ "", "/" })
    public String createOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
        log.debug("CREATE a new recipe, or UPDATE a specific recipe, then redirect to SHOW.");
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.debug(error.toString());
            });
            return RECIPE_CREATE_OR_UPDATE_VIEW;
        }
        RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(command);
        return "redirect:/recipes/" + savedCommand.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable String id, Model model) {
        log.debug("SHOW info about a specific recipe.");
        model.addAttribute("recipe", this.recipeService.findById(id));
        return "recipes/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        log.debug("EDIT form for a specific recipe.");
        model.addAttribute("recipe", this.recipeService.findCommandById(id));
        return RECIPE_CREATE_OR_UPDATE_VIEW;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        log.debug("DELETE a specific recipe, then redirect to LIST.");
        this.recipeService.deleteById(id);
        return "redirect:/recipes";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(Exception ex) {
        log.error("Handling Not Found Exception: " + ex.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.setViewName("404error");
        return mav;
    }

}
