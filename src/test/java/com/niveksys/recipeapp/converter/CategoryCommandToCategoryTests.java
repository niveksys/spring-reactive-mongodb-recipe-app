package com.niveksys.recipeapp.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.niveksys.recipeapp.command.CategoryCommand;
import com.niveksys.recipeapp.model.Category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryCommandToCategoryTests {

    public static final String CATEGORY_ID = "1";
    public static final String DESCRIPTION = "description";

    CategoryCommandToCategory converter;

    @BeforeEach
    public void setUp() {
        this.converter = new CategoryCommandToCategory();
    }

    @Test
    public void convertNullObject() throws Exception {
        assertNull(this.converter.convert(null));
    }

    @Test
    public void convertEmptyObject() throws Exception {
        assertNotNull(this.converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws Exception {
        // given
        CategoryCommand command = new CategoryCommand();
        command.setId(CATEGORY_ID);
        command.setDescription(DESCRIPTION);

        // when
        Category category = this.converter.convert(command);

        // then
        assertEquals(CATEGORY_ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }

}
