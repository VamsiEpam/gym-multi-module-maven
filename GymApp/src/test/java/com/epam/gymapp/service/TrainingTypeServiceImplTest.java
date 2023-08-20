package com.epam.gymapp.service;

import com.epam.gymapp.customexceptions.UserException;
import com.epam.gymapp.model.TrainingType;
import com.epam.gymapp.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingTypeServiceImplTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTrainingType() {
        // Test data
        String trainingName = "Strength Training";

        // Test
        assertDoesNotThrow(() -> {
            trainingTypeService.addTrainingType(trainingName);
        });

        verify(trainingTypeRepository).save(any(TrainingType.class));
    }

    @Test
    void testGetTrainingType() {
        // Mocking
        TrainingType trainingType = new TrainingType();
        when(trainingTypeRepository.findById(anyInt())).thenReturn(Optional.of(trainingType));

        // Test
        TrainingType result = trainingTypeService.getTrainingType(1);

        assertNotNull(result);
    }

    @Test
    void testGetTrainingTypeNotFound() {
        // Mocking
        when(trainingTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Test and assert exception
        assertThrows(UserException.class, () -> {
            trainingTypeService.getTrainingType(1);
        });
    }
}
