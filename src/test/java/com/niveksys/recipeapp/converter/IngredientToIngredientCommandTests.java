package com.niveksys.recipeapp.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import com.niveksys.recipeapp.command.IngredientCommand;
import com.niveksys.recipeapp.model.Ingredient;
import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.model.UnitOfMeasure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IngredientToIngredientCommandTests {
    public static final String INGREDIENT_ID = "1";
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final String UOM_ID = "1";
    public static final Recipe RECIPE = new Recipe();

    IngredientToIngredientCommand converter;

    @BeforeEach
    public void setUp() {
        this.converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void convertNullObject() throws Exception {
        assertNull(this.converter.convert(null));
    }

    @Test
    public void convertEmptyObject() throws Exception {
        assertNotNull(this.converter.convert(new Ingredient()));
    }

    @Test
    public void convert() throws Exception {
        // given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        ingredient.setUom(uom);

        // when
        IngredientCommand command = converter.convert(ingredient);

        // then
        assertNotNull(command);
        assertEquals(INGREDIENT_ID, command.getId());
        assertEquals(AMOUNT, command.getAmount());
        assertEquals(DESCRIPTION, command.getDescription());
        assertNotNull(command.getUom());
        assertEquals(UOM_ID, command.getUom().getId());
    }

    @Test
    public void convertWithNullUOM() throws Exception {
        // given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);

        // when
        IngredientCommand command = converter.convert(ingredient);

        // then
        assertNotNull(command);
        assertEquals(INGREDIENT_ID, command.getId());
        assertEquals(AMOUNT, command.getAmount());
        assertEquals(DESCRIPTION, command.getDescription());
        assertNull(command.getUom());
    }
}
