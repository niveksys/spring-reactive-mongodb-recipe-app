package com.niveksys.recipeapp.repository.reactive;

import com.niveksys.recipeapp.model.UnitOfMeasure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {
}
