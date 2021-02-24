package com.niveksys.recipeapp.service;

import java.util.Set;

import com.niveksys.recipeapp.command.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> getUomCommands();
}
