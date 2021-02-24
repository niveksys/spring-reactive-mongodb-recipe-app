package com.niveksys.recipeapp.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.niveksys.recipeapp.command.NotesCommand;
import com.niveksys.recipeapp.model.Notes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotesToNotesCommandTests {

    public static final String NOTES_ID = "1";
    public static final String RECIPE_NOTES = "Notes";

    NotesToNotesCommand converter;

    @BeforeEach
    public void setUp() {
        this.converter = new NotesToNotesCommand();
    }

    @Test
    public void convertNullObject() throws Exception {
        assertNull(this.converter.convert(null));
    }

    @Test
    public void convertEmptyObject() throws Exception {
        assertNotNull(this.converter.convert(new Notes()));
    }

    @Test
    public void convert() throws Exception {
        // given
        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        notes.setRecipeNotes(RECIPE_NOTES);

        // when
        NotesCommand command = this.converter.convert(notes);

        // then
        assertNotNull(command);
        assertEquals(NOTES_ID, command.getId());
        assertEquals(RECIPE_NOTES, command.getRecipeNotes());
    }
}
