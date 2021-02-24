package com.niveksys.recipeapp.service;

import com.niveksys.recipeapp.command.UnitOfMeasureCommand;

import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> getUomCommands();
}
