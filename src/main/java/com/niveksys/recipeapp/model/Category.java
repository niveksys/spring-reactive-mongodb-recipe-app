package com.niveksys.recipeapp.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Category {
    @Id
    private String id;

    private String description;
    private Set<Recipe> recipes;
}
