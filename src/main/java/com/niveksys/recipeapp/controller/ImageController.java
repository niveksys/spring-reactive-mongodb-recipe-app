package com.niveksys.recipeapp.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import com.niveksys.recipeapp.command.RecipeCommand;
import com.niveksys.recipeapp.service.ImageService;
import com.niveksys.recipeapp.service.RecipeService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes/{id}/image/new")
    public String newImage(@PathVariable String id, Model model) {
        log.debug("NEW image form.");
        model.addAttribute("recipe", this.recipeService.findCommandById(id));
        return "recipes/imageForm";
    }

    @PostMapping("/recipes/{id}/image")
    public String createOrUpdate(@PathVariable String id, @RequestParam("imageFile") MultipartFile file) {
        log.debug("CREATE a new image, or UPDATE a specific image, then redirect to SHOW.");
        this.imageService.saveImageFile(id, file);
        return "redirect:/recipes/" + id;
    }

    @GetMapping("/recipes/{id}/image")
    public void show(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand command = this.recipeService.findCommandById(id);

        if (command.getImage() != null) {
            byte[] bytes = new byte[command.getImage().length];
            int i = 0;
            for (Byte b : command.getImage()) {
                bytes[i++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(bytes);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
