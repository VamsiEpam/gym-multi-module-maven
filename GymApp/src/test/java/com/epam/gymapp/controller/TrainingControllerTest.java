package com.epam.gymapp.controller;

import com.epam.gymapp.dto.TrainingOverallDTO;
import com.epam.gymapp.dto.report.NotificationDTO;
import com.epam.gymapp.dto.report.TrainingMailDTO;
import com.epam.gymapp.dto.request.TrainingRequest;
import com.epam.gymapp.kafka.NotificationProducer;
import com.epam.gymapp.kafka.ReportProducer;
import com.epam.gymapp.service.TrainingServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

  class TrainingControllerTest {

    @Mock
    private TrainingServiceImpl trainingService;

    @Mock
    private ReportProducer reportProducer;

    @Mock
    private NotificationProducer notificationProducer;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
      void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
      void testAddTraining_Success() throws JsonProcessingException {
        // Create TrainingRequest object
        TrainingRequest trainingDetails = new TrainingRequest();
        trainingDetails.setTrainingDate(LocalDate.of(12,1,1));
        trainingDetails.setTrainingDuration(10);
        trainingDetails.setTrainingName("varerge");
        trainingDetails.setTraineeUserName("WCRAWEV");
        trainingDetails.setTrainerUserName("wecvwrgo");
        // Set training details

        TrainingMailDTO trainingMailDTO = TrainingMailDTO.builder().build();
        trainingMailDTO.setTraineeEmail("svwsvs");
        trainingMailDTO.setTrainerEmail("svsv");
        TrainingOverallDTO trainingOverallDTO = new TrainingOverallDTO();
        trainingOverallDTO.setTrainingMailDTO(trainingMailDTO);
        // Set training overall details

        when(trainingService.addTraining(any(TrainingRequest.class))).thenReturn(trainingOverallDTO);

        ResponseEntity<Void> response = trainingController.addTraining(trainingDetails);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(trainingService).addTraining(trainingDetails);
        verify(reportProducer).sendGymReport(trainingOverallDTO.getGymReportDTO());
        verify(notificationProducer).sendTrainingNotification(any(NotificationDTO.class));
    }

    @Test
      void testGetTraining_Success() throws JsonProcessingException {
        // Create a training name
        String trainingName = "testTrainingName";

        TrainingRequest trainingRequest = new TrainingRequest();
        // Set training details

        when(trainingService.getTraining(trainingName)).thenReturn(trainingRequest);

        ResponseEntity<TrainingRequest> response = trainingController.getTraining(trainingName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainingRequest, response.getBody());

        verify(trainingService).getTraining(trainingName);
    }

    // Add more test cases to cover different scenarios and edge cases
}
