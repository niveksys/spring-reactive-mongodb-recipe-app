package com.niveksys.recipeapp.repository.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.niveksys.recipeapp.model.UnitOfMeasure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTests {

    public static final String EACH = "Each";

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSaveUom() throws Exception {
        // given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(EACH);

        // when
        this.unitOfMeasureReactiveRepository.save(uom).block();

        // then
        Long count = this.unitOfMeasureReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), count);

    }

    @Test
    public void testFindByDescription() throws Exception {
        // given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(EACH);

        // when
        this.unitOfMeasureReactiveRepository.save(uom).block();

        // then
        UnitOfMeasure fetchedUOM = this.unitOfMeasureReactiveRepository.findByDescription(EACH).block();
        assertEquals(EACH, fetchedUOM.getDescription());
    }

}
