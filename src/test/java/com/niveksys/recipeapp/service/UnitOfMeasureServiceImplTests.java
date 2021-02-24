package com.niveksys.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.niveksys.recipeapp.command.UnitOfMeasureCommand;
import com.niveksys.recipeapp.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.niveksys.recipeapp.model.UnitOfMeasure;
import com.niveksys.recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
public class UnitOfMeasureServiceImplTests {

    @Mock
    // UnitOfMeasureRepository unitOfMeasureRepository;
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    UnitOfMeasureServiceImpl uomService;

    public UnitOfMeasureServiceImplTests() {
        this.unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @BeforeEach
    public void setUp() {
        this.uomService = new UnitOfMeasureServiceImpl(this.unitOfMeasureReactiveRepository,
                this.unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void getUomCommands() throws Exception {
        // given
        Set<UnitOfMeasure> uoms = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("1");
        uoms.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("2");
        uoms.add(uom2);

        when(this.unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

        // when
        List<UnitOfMeasureCommand> commands = this.uomService.getUomCommands().collectList().block();

        // then
        assertEquals(2, commands.size());
        verify(this.unitOfMeasureReactiveRepository, times(1)).findAll();
    }
}
