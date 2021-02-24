package com.niveksys.recipeapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.service.ImageService;
import com.niveksys.recipeapp.service.RecipeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ImageController.class)
public class ImageControllerTests {
    @MockBean
    ImageService imageService;

    @MockBean
    RecipeService recipeService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void newImage() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId("1");

        when(recipeService.findCommandById(anyString())).thenReturn(command);

        // when
        this.mockMvc.perform(get("/recipes/1/image/new")).andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        // then
        verify(this.recipeService, times(1)).findCommandById(anyString());

    }

    @Test
    public void createOrUpdate() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain",
                "Mock Image Byte Stream".getBytes());

        // when
        this.mockMvc.perform(multipart("/recipes/1/image").file(multipartFile)).andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipes/1"));

        // then
        verify(this.imageService, times(1)).saveImageFile(anyString(), any());
    }

    @Test
    public void show() throws Exception {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId("1");

        String s = "Mock Image Byte Stream";
        Byte[] bytes = new Byte[s.getBytes().length];

        int i = 0;
        for (byte b : s.getBytes()) {
            bytes[i++] = b;
        }
        command.setImage(bytes);

        when(this.recipeService.findCommandById(anyString())).thenReturn(command);

        // when
        MockHttpServletResponse response = this.mockMvc.perform(get("/recipes/1/image")).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        byte[] reponseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, reponseBytes.length);
    }

}
