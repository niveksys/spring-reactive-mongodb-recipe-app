package com.niveksys.recipeapp.repository.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.niveksys.recipeapp.model.Category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CategoryReactiveRepositoryTests {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {
        this.categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void save() throws Exception {
        // given
        Category category = new Category();
        category.setDescription("Foo");

        // when
        this.categoryReactiveRepository.save(category).block();

        // then
        Long count = this.categoryReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void testFindByDescription() throws Exception {
        // given
        Category category = new Category();
        category.setDescription("Foo");

        // when
        this.categoryReactiveRepository.save(category).block();

        // then
        Category fetchedCat = categoryReactiveRepository.findByDescription("Foo").block();
        assertNotNull(fetchedCat.getId());
    }
}
