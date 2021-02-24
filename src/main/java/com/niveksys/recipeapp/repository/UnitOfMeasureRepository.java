package com.niveksys.recipeapp.repository;

import java.util.Optional;

import com.niveksys.recipeapp.model.UnitOfMeasure;

import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
