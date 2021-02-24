package com.niveksys.recipeapp.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.niveksys.recipeapp.command.CategoryCommand;
import com.niveksys.recipeapp.model.Category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryToCategoryCommandTests {

    public static final String CATEGORY_ID = "1";
    public static final String DESCRIPTION = "description";

    CategoryToCategoryCommand converter;

    @BeforeEach
    public void setUp() throws Exception {
        this.converter = new CategoryToCategoryCommand();
    }

    @Test
    public void convertNullObject() {
        assertNull(this.converter.convert(null));
    }

    @Test
    public void convertEmptyObject() {
        assertNotNull(this.converter.convert(new Category()));
    }

    @Test
    public void convert() {
        // given
        Category category = new Category();
        category.setId(CATEGORY_ID);
        category.setDescription(DESCRIPTION);

        // when
        CategoryCommand categoryCommand = this.converter.convert(category);

        // then
        assertEquals(CATEGORY_ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }

}
