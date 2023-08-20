package com.epam.gymapp.controller;


import com.epam.gymapp.dto.CredentialsDetails;
import com.epam.gymapp.dto.report.NotificationDTO;
import com.epam.gymapp.dto.request.TraineeRegistration;
import com.epam.gymapp.dto.request.TraineeTrainingsRequest;
import com.epam.gymapp.dto.request.TraineeUpdateRequest;
import com.epam.gymapp.dto.request.TraineesTrainerUpdateRequest;
import com.epam.gymapp.dto.response.TraineeDetails;
import com.epam.gymapp.dto.response.TrainingResponse;
import com.epam.gymapp.kafka.NotificationProducer;
import com.epam.gymapp.service.TraineeServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TraineeControllerTest {

    @Mock
    private TraineeServiceImpl traineeService;

    @Mock
    private NotificationProducer notificationProducer;

    @InjectMocks
    private TraineeController traineeController;

    TraineeRegistration registration = new TraineeRegistration();
    CredentialsDetails credentialsDetails = new CredentialsDetails();


    TraineeDetails traineeDetails = new TraineeDetails();
    @BeforeEach
      void setUp() {
        MockitoAnnotations.openMocks(this);
        registration.setAddress("kqjfeh");
        registration.setEmail("wrgrlg");
        registration.setDateOfBirth(LocalDate.of(12,1,1));
        registration.setLastName("ageaega");
        registration.setFirstName("sgjrwk");
        // Set registration details


        credentialsDetails.setPassword("sgsdgsg");
        credentialsDetails.setUserName("sdgkjeagfeai");
    }

    @Test
     void testRegisterTrainee_Success() throws JsonProcessingException {



        when(traineeService.createTrainee(any(TraineeRegistration.class))).thenReturn(credentialsDetails);
        Mockito.doNothing().when(notificationProducer).sendUpdateNotification(any());



        assertDoesNotThrow(() -> traineeController.registerTrainee(registration));

        verify(traineeService).createTrainee(registration);
        }

    @Test
    void testGetTrainee_Success() {
        String username = "testUsername";
        // Set trainee details

        when(traineeService.getTrainee(username)).thenReturn(traineeDetails);

        ResponseEntity<TraineeDetails> response = traineeController.getTrainee(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(traineeDetails, response.getBody());

        verify(traineeService).getTrainee(username);
    }

    @Test
      void testUpdateTrainee_Success() throws JsonProcessingException {
        // Create TraineeUpdateRequest object
        TraineeUpdateRequest updateRequest = new TraineeUpdateRequest();
        // Set update details
        updateRequest.setEmail("rwgwrf");
        updateRequest.setAddress("wwefw");
        updateRequest.setLastName("wefwd");
        updateRequest.setFirstName("wwfd");
        updateRequest.setDob(LocalDate.of(12,1,1));
        updateRequest.setUserName("agfwsfac");
        updateRequest.setActive(true);

        TraineeDetails traineeDetails = new TraineeDetails();
        // Set updated trainee details

        when(traineeService.updateTrainee(any(TraineeUpdateRequest.class))).thenReturn(traineeDetails);

        ResponseEntity<TraineeDetails> response = traineeController.updateTrainee(updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(traineeDetails, response.getBody());

        verify(traineeService).updateTrainee(updateRequest);
        // Verify notificationProducer.sendUpdateNotification method if applicable
    }

    @Test
      void testDeleteTrainee_Success() {
        // Create a username
        String username = "testUsername";

        ResponseEntity<Void> response = traineeController.deleteTrainee(username);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(traineeService).removeTrainee(username);
    }

    @Test
      void testUpdateTraineesTrainerList_Success() {
        // Create TraineesTrainerUpdateRequest object
        TraineesTrainerUpdateRequest updateRequest = new TraineesTrainerUpdateRequest();
        updateRequest.setTraineeUserName("fadfagfr");
        updateRequest.setTrainersUserName(new ArrayList<>());
        // Set update details

        TraineeDetails traineeDetails = new TraineeDetails();
        // Set updated trainee details

        when(traineeService.updateTraineesTrainerList(any(TraineesTrainerUpdateRequest.class))).thenReturn(traineeDetails);

        ResponseEntity<TraineeDetails> response = traineeController.updateTraineesTrainerList(updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(traineeDetails, response.getBody());

        verify(traineeService).updateTraineesTrainerList(updateRequest);
    }

    @Test
      void testGetTraineeTrainingsList_Success() {
        // Create TraineeTrainingsRequest object
        TraineeTrainingsRequest traineeTrainingsRequest = new TraineeTrainingsRequest();
        traineeTrainingsRequest.setTrainingTypeId(1);
        traineeTrainingsRequest.setUserName("afaleiuoe");
        traineeTrainingsRequest.setEndDate(LocalDate.of(12,1,1));
        traineeTrainingsRequest.setStartDate(LocalDate.of(12,1,1));
        traineeTrainingsRequest.setTrainerName("afea");
        // Set trainee trainings request details

        List<TrainingResponse> trainingResponseList = List.of(new TrainingResponse(), new TrainingResponse());
        // Set training response list

        when(traineeService.getTraineeTrainings(traineeTrainingsRequest)).thenReturn(trainingResponseList);

        ResponseEntity<List<TrainingResponse>> response = traineeController.getTraineeTrainingsList(traineeTrainingsRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainingResponseList, response.getBody());

        verify(traineeService).getTraineeTrainings(traineeTrainingsRequest);
    }
}

