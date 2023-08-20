package com.epam.gymapp.controller;

import com.epam.gymapp.dto.CredentialsDetails;
import com.epam.gymapp.dto.request.TrainerRegistration;
import com.epam.gymapp.dto.request.TrainerTrainings;
import com.epam.gymapp.dto.request.TrainerUpdateRequest;
import com.epam.gymapp.dto.response.TrainerBasicDetails;
import com.epam.gymapp.dto.response.TrainerDetails;
import com.epam.gymapp.dto.response.TrainingResponse;
import com.epam.gymapp.kafka.NotificationProducer;
import com.epam.gymapp.service.TrainerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

  class TrainerControllerTest {

    @Mock
    private TrainerServiceImpl trainerService;

    @Mock
    private NotificationProducer notificationProducer;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
      void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
      void testRegisterTrainer_Success() throws JsonProcessingException {
        // Create TrainerRegistration object
        TrainerRegistration registration = new TrainerRegistration();
        registration.setEmail("aefafea");
        registration.setFirstName("zceaca");
        registration.setLastName("acaeca");
        registration.setSpecialization("afacae");
        // Set registration details

        CredentialsDetails credentialsDetails = new CredentialsDetails();
        // Set credentials details

        when(trainerService.createTrainer(any(TrainerRegistration.class))).thenReturn(credentialsDetails);

        ResponseEntity<CredentialsDetails> response = trainerController.registerTrainer(registration);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(credentialsDetails, response.getBody());

        verify(trainerService).createTrainer(registration);
        // Verify notificationProducer.sendCredentialsNotification method if applicable
    }

    @Test
      void testGetTrainer_Success() {
        // Create a username
        String username = "testUsername";

        TrainerDetails trainerDetails = new TrainerDetails();
        // Set trainer details

        when(trainerService.getTrainer(username)).thenReturn(trainerDetails);

        ResponseEntity<TrainerDetails> response = trainerController.getTrainer(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainerDetails, response.getBody());

        verify(trainerService).getTrainer(username);
    }

    @Test
      void testUpdateTrainer_Success() throws JsonProcessingException {
        // Create TrainerUpdateRequest object
        TrainerUpdateRequest updateRequest = new TrainerUpdateRequest();
        updateRequest.setEmail("aceac");
        updateRequest.setFirstName("caeeaco");
        updateRequest.setLastName("aeckeyc");
        updateRequest.setActive(true);
        updateRequest.setUserName("aeckejyhfa");
        // Set update details

        TrainerDetails trainerDetails = new TrainerDetails();
        // Set updated trainer details

        when(trainerService.updateTrainer(any(TrainerUpdateRequest.class))).thenReturn(trainerDetails);

        ResponseEntity<TrainerDetails> response = trainerController.updateTrainer(updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainerDetails, response.getBody());

        verify(trainerService).updateTrainer(updateRequest);
        // Verify notificationProducer.sendUpdateNotification method if applicable
    }

    @Test
      void testDeleteTrainer_Success() {
        // Create a username
        String username = "testUsername";

        ResponseEntity<Void> response = trainerController.deleteTrainer(username);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verify trainerService.removeTrainer method if applicable
    }

    @Test
      void testGetTrainerNotAssignedOnTrainee_Success() {
        // Create a trainee username
        String traineeUsername = "testTraineeUsername";

        List<TrainerBasicDetails> trainerDetailsList = List.of(new TrainerBasicDetails(), new TrainerBasicDetails());
        // Set trainer basic details list

        when(trainerService.getTraineeNotAssignedActiveTrainers(traineeUsername)).thenReturn(trainerDetailsList);

        ResponseEntity<List<TrainerBasicDetails>> response = trainerController.getTrainerNotAssignedOnTrainee(traineeUsername);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainerDetailsList, response.getBody());

        verify(trainerService).getTraineeNotAssignedActiveTrainers(traineeUsername);
    }

    @Test
      void testGetTrainerTrainingsList_Success() {
        // Create TrainerTrainings object
        TrainerTrainings trainerTrainings = new TrainerTrainings();
        trainerTrainings.setEndDate(LocalDate.of(12,1,1));
        trainerTrainings.setUserName("afeeiyfe");
        trainerTrainings.setStartDate(LocalDate.of(12,1,1));
        trainerTrainings.setTraineeName("afleiya");

        // Set trainer trainings details

        List<TrainingResponse> trainingResponseList = List.of(new TrainingResponse(), new TrainingResponse());
        // Set training response list

        when(trainerService.getTrainerTrainings(trainerTrainings)).thenReturn(trainingResponseList);

        ResponseEntity<List<TrainingResponse>> response = trainerController.getTrainerTrainingsList(trainerTrainings);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainingResponseList, response.getBody());

        verify(trainerService).getTrainerTrainings(trainerTrainings);
    }

    // Add more test cases to cover different scenarios and edge cases
}

