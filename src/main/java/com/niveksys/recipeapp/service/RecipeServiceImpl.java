package com.niveksys.recipeapp.service;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.converter.RecipeCommandToRecipe;
import com.niveksys.recipeapp.converter.RecipeToRecipeCommand;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.reactive.RecipeReactiveRepository;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RecipeServiceImpl implements RecipeService {

    // private final RecipeRepository recipeRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
            RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        // Set<Recipe> recipeSet = new HashSet<>();
        // recipeRepository.findAll().forEach(recipeSet::add);
        // return recipeSet;
        return this.recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        // Optional<Recipe> recipeOptional = this.recipeRepository.findById(id);
        // if (!recipeOptional.isPresent()) {
        // throw new NotFoundException("Recipe Not Found with ID: " + id);
        // }
        // return recipeOptional.get();
        return this.recipeReactiveRepository.findById(id);
    }

    @Override
    // @Transactional
    public Mono<RecipeCommand> findCommandById(String id) {
        // RecipeCommand command = recipeToRecipeCommand.convert(findById(id));

        // // enhance command object with id value
        // if (command.getIngredients() != null && command.getIngredients().size() > 0)
        // {
        // command.getIngredients().forEach(ingredient -> {
        // ingredient.setRecipeId(command.getId());
        // });
        // }
        // return command;
        return this.recipeReactiveRepository.findById(id).map(recipe -> {
            RecipeCommand command = this.recipeToRecipeCommand.convert(recipe);
            command.getIngredients().forEach(ingredient -> {
                ingredient.setRecipeId(command.getId());
            });
            return command;
        });
    }

    @Override
    // @Transactional
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        // Recipe detectedRecipe = this.recipeCommandToRecipe.convert(command);

        // Recipe savedRecipe = this.recipeRepository.save(detectedRecipe);
        // log.debug("Saved Recipe ID: " + savedRecipe.getId());
        // return this.recipeToRecipeCommand.convert(savedRecipe);
        return this.recipeReactiveRepository.save(this.recipeCommandToRecipe.convert(command))
                .map(this.recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        // this.recipeRepository.deleteById(id);
        return this.recipeReactiveRepository.deleteById(id);
    }

}
