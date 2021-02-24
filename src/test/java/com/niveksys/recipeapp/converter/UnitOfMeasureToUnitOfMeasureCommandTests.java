package com.niveksys.recipeapp.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.niveksys.recipeapp.command.UnitOfMeasureCommand;
import com.niveksys.recipeapp.model.UnitOfMeasure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitOfMeasureToUnitOfMeasureCommandTests {

    public static final String UOM_ID = "1";
    public static final String DESCRIPTION = "description";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    public void setUp() {
        this.converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void convertNullObject() throws Exception {
        assertNull(this.converter.convert(null));
    }

    @Test
    public void convertEmptyObject() throws Exception {
        assertNotNull(this.converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() throws Exception {
        // given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        uom.setDescription(DESCRIPTION);

        // when
        UnitOfMeasureCommand command = this.converter.convert(uom);

        // then
        assertNotNull(command);
        assertEquals(UOM_ID, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }

}
