package com.niveksys.recipeapp.repository.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.niveksys.recipeapp.model.Recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RecipeReactiveRepositoryTests {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void testRecipeSave() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setDescription("Yummy");

        // when
        this.recipeReactiveRepository.save(recipe).block();

        // then
        Long count = this.recipeReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), count);
    }

}
