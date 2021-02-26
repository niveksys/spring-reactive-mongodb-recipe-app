package com.niveksys.recipeapp.service;

import com.niveksys.recipeapp.command.UnitOfMeasureCommand;
import com.niveksys.recipeapp.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.niveksys.recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    // private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
            UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> getUomCommands() {
        // return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(),
        // false)
        // .map(unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());
        return this.unitOfMeasureReactiveRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }

}
