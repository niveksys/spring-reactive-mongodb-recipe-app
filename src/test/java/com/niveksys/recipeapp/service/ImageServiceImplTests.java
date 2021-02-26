package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.repository.reactive.RecipeReactiveRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTests {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @Captor
    ArgumentCaptor<Recipe> recipeCaptor;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void saveImageFile() throws Exception {
        // given
        String id = "1";
        MultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain",
                "Mock Image Byte Stream".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(this.recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(this.recipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));

        // when
        this.imageService.saveImageFile(id, multipartFile);

        // then
        verify(this.recipeReactiveRepository, times(1)).save(this.recipeCaptor.capture());
        Recipe savedRecipe = recipeCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}
