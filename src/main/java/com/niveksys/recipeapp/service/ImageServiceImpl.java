package com.niveksys.recipeapp.service;

import java.io.IOException;

import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.reactive.RecipeReactiveRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    // private final RecipeRepository recipeRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    // @Transactional
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
        log.debug("Received a file.");
        // try {
        // Recipe recipe = recipeRepository.findById(recipeId).get();

        // Byte[] bytes = new Byte[file.getBytes().length];
        // int i = 0;
        // for (byte b : file.getBytes()) {
        // bytes[i++] = b;
        // }

        // recipe.setImage(bytes);

        // recipeRepository.save(recipe);
        // } catch (IOException e) {
        // log.error("Error encountered at saving image: ", e);
        // e.printStackTrace();
        // }
        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId).map(recipe -> {
            try {
                Byte[] bytes = new Byte[file.getBytes().length];
                int i = 0;
                for (byte b : file.getBytes()) {
                    bytes[i++] = b;
                }
                recipe.setImage(bytes);
                return recipe;
            } catch (IOException e) {
                log.error("Error encountered at saving image: ", e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        this.recipeReactiveRepository.save(recipeMono.block()).block();
        return Mono.empty();
    }

}
