package com.epam.gymapp.controller;

import com.epam.gymapp.model.StringConstants;
import com.epam.gymapp.service.TrainingTypeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
  class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeServiceImpl trainingTypeService;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @BeforeEach
      void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
      void testAddTrainingTypeName_Success() {
        String trainingName = "TestTraining";

        ResponseEntity<Void> response = trainingTypeController.addTrainingTypeName(trainingName);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(trainingTypeService).addTrainingType(trainingName);

          }

}
