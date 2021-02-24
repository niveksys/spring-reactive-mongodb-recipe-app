package com.niveksys.recipeapp.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.niveksys.recipeapp.command.NotesCommand;
import com.niveksys.recipeapp.model.Notes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotesCommandToNotesTests {

    public static final String NOTES_ID = "1";
    public static final String RECIPE_NOTES = "Notes";

    NotesCommandToNotes converter;

    @BeforeEach
    public void setUp() {
        this.converter = new NotesCommandToNotes();
    }

    @Test
    public void convertNullObject() throws Exception {
        assertNull(this.converter.convert(null));
    }

    @Test
    public void convertEmptyObject() throws Exception {
        assertNotNull(this.converter.convert(new NotesCommand()));
    }

    @Test
    public void convert() throws Exception {
        // given
        NotesCommand command = new NotesCommand();
        command.setId(NOTES_ID);
        command.setRecipeNotes(RECIPE_NOTES);

        // when
        Notes notes = this.converter.convert(command);

        // then
        assertNotNull(notes);
        assertEquals(NOTES_ID, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}
