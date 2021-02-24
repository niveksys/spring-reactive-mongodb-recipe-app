package com.niveksys.recipeapp.repository;

import java.util.Optional;

import com.niveksys.recipeapp.model.Category;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
    Optional<Category> findByDescription(String description);
}
