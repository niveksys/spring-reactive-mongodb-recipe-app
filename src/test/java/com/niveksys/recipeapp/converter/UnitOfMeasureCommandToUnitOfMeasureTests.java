package com.niveksys.recipeapp.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.niveksys.recipeapp.command.UnitOfMeasureCommand;
import com.niveksys.recipeapp.model.UnitOfMeasure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitOfMeasureCommandToUnitOfMeasureTests {

    public static final String UOM_ID = "1";
    public static final String DESCRIPTION = "description";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    public void setUp() {
        this.converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void convertNullObject() throws Exception {
        assertNull(this.converter.convert(null));
    }

    @Test
    public void convertEmptyObject() throws Exception {
        assertNotNull(this.converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() throws Exception {
        // given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(UOM_ID);
        command.setDescription(DESCRIPTION);

        // when
        UnitOfMeasure uom = this.converter.convert(command);

        // then
        assertNotNull(uom);
        assertEquals(UOM_ID, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}
