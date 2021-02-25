package com.niveksys.recipeapp.service;

import java.util.Optional;

import com.niveksys.recipeapp.command.IngredientCommand;
import com.niveksys.recipeapp.converter.IngredientCommandToIngredient;
import com.niveksys.recipeapp.converter.IngredientToIngredientCommand;
import com.niveksys.recipeapp.model.Ingredient;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.reactive.RecipeReactiveRepository;
import com.niveksys.recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
            UnitOfMeasureReactiveRepository unitOfMeasureRepository,
            IngredientToIngredientCommand ingredientToIngredientCommand,
            IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        // Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        // if (!recipeOptional.isPresent()) {
        // log.error("Recipe not found with ID: " + recipeId);
        // }
        // Recipe recipe = recipeOptional.get();

        // Optional<IngredientCommand> commandOptional =
        // recipe.getIngredients().stream()
        // .filter(ingredient ->
        // ingredient.getId().equals(ingredientId)).peek(System.out::println)
        // .map(ingredient ->
        // ingredientToIngredientCommand.convert(ingredient)).peek(System.out::println)
        // .findFirst();

        // if (!commandOptional.isPresent()) {
        // log.error("Ingredient not found with ID: " + ingredientId);
        // }
        // return Mono.just(commandOptional.get());

        return this.recipeReactiveRepository.findById(recipeId).flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId)).single().map(ingredient -> {
                    IngredientCommand command = this.ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        // Optional<Recipe> recipeOptional =
        // recipeRepository.findById(command.getRecipeId());
        Recipe recipe = this.recipeReactiveRepository.findById(command.getRecipeId()).block();

        // if (!recipeOptional.isPresent()) {
        if (recipe == null) {
            log.error("Recipe not found with ID: " + command.getRecipeId());
            return Mono.just(new IngredientCommand());
        } else {
            // Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setDescription(command.getDescription());
                ingredient.setAmount(command.getAmount());
                ingredient.setUom(unitOfMeasureRepository
                        // .findById(command.getUom().getId())
                        // .orElseThrow(() -> new RuntimeException("UOM not found.")));
                        .findById(command.getUom().getId()).block());
                if (ingredient.getUom() == null) {
                    new RuntimeException("UOM not found.");
                }
            } else {
                // add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            // Recipe savedRecipe = recipeRepository.save(recipe);
            Recipe savedRecipe = this.recipeReactiveRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                        .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId())).findFirst();
            }

            // enhance with id value
            IngredientCommand ingredientCommandSaved = this.ingredientToIngredientCommand
                    .convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
        }
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String id) {
        log.debug("Deleting ingredient with ID: " + recipeId + ":" + id);

        // Optional<Recipe> recipeOptional = this.recipeRepository.findById(recipeId);
        Recipe recipe = this.recipeReactiveRepository.findById(recipeId).block();

        // if (recipeOptional.isPresent()) {
        // Recipe recipe = recipeOptional.get();
        if (recipe != null) {
            log.debug("Found recipe with ID: " + recipeId);

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(id)).findFirst();

            if (ingredientOptional.isPresent()) {
                log.debug("Found ingredient with ID: " + id);
                Ingredient ingredient = ingredientOptional.get();
                recipe.getIngredients().remove(ingredient);
                // recipeRepository.save(recipe);
                this.recipeReactiveRepository.save(recipe).block();
            }
        } else {
            log.debug("Recipe not found with ID: " + recipeId);
        }
        return Mono.empty();
    }

}
