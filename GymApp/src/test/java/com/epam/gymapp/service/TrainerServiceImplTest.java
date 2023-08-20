package com.epam.gymapp.service;

import com.epam.gymapp.customexceptions.UserException;
import com.epam.gymapp.dto.CredentialsDetails;
import com.epam.gymapp.dto.request.TrainerRegistration;
import com.epam.gymapp.dto.request.TrainerTrainings;
import com.epam.gymapp.dto.request.TrainerUpdateRequest;
import com.epam.gymapp.helper.CredentialGenerator;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.TrainingType;
import com.epam.gymapp.model.User;
import com.epam.gymapp.repository.TrainerRepository;
import com.epam.gymapp.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private CredentialGenerator credentialGenerator;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TrainingServiceImpl trainingService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainer() {
        // Test data
        TrainerRegistration trainerDetails = new TrainerRegistration();
        trainerDetails.setFirstName("John");
        trainerDetails.setLastName("Doe");
        trainerDetails.setEmail("john.doe@example.com");
        trainerDetails.setSpecialization("Fitness");

        CredentialsDetails credentialsDetails = new CredentialsDetails();
        credentialsDetails.setUserName("john.doe");
        credentialsDetails.setPassword("password");

        User user = new User();
        user.setId(1);
        user.setUserName("john.doe");

        when(credentialGenerator.generateCredentials(any(User.class))).thenReturn(credentialsDetails);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(trainingTypeRepository.findByTrainingTypeName(anyString())).thenReturn(Optional.of(new TrainingType()));

        // Test
        assertDoesNotThrow(() -> trainerService.createTrainer(trainerDetails));
    }

    @Test
    void testGetTrainer() {
        // Test data
        String username = "john.doe";
        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setTraineeList(List.of(trainee));
        trainer.setSpecialization(new TrainingType());
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));

        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");
        user.setTrainee(trainee);
        user.setTrainer(trainer);
        when(userService.getUser(username)).thenReturn(user);
        when(trainerRepository.findByUserId(anyInt())).thenReturn(trainer);

        // Test
        assertDoesNotThrow(() -> trainerService.getTrainer(username));
    }

    @Test
    void testGetTrainerException() {
        // Test data
        String username = "john.doe";
        User user = new User();
        when(userService.getUser(username)).thenReturn(user);

        // Test
        assertThrows(UserException.class,() -> trainerService.getTrainer(username));
    }

    @Test
    void testUpdateTrainer() {
        // Test data
        TrainerUpdateRequest trainerUpdateRequest = new TrainerUpdateRequest();
        trainerUpdateRequest.setUserName("john.doe");
        trainerUpdateRequest.setFirstName("John");
        trainerUpdateRequest.setLastName("Doe");
        trainerUpdateRequest.setEmail("john.doe@example.com");
        trainerUpdateRequest.setActive(true);

        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));

        trainer.setTraineeList(List.of(trainee));
        trainer.setSpecialization(new TrainingType());
        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");
        user.setTrainee(trainee);
        user.setTrainer(trainer);
        when(userService.getUser(anyString())).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(trainerRepository.findByUserId(anyInt())).thenReturn(trainer);

        // Test
        assertDoesNotThrow(() -> trainerService.updateTrainer(trainerUpdateRequest));
    }

    @Test
    void testUpdateTrainerException() {
        // Test data
        TrainerUpdateRequest trainerUpdateRequest = new TrainerUpdateRequest();
        trainerUpdateRequest.setUserName("john.doe");
        trainerUpdateRequest.setFirstName("John");
        trainerUpdateRequest.setLastName("Doe");
        trainerUpdateRequest.setEmail("john.doe@example.com");
        trainerUpdateRequest.setActive(true);

        User user = new User();

        when(userService.getUser(anyString())).thenReturn(user);

        // Test
        assertThrows(UserException.class,() -> trainerService.updateTrainer(trainerUpdateRequest));
    }

    @Test
    void testGetTraineeNotAssignedActiveTrainers() {
        // Test data
        String traineeName = "jane.doe";
        User user = new User();
        user.setId(1);
        user.setTrainee(new Trainee());
        when(userService.getUser(traineeName)).thenReturn(user);
        when(trainerRepository.findTrainersNotInList(anyList())).thenReturn(new ArrayList<>());

        // Test
        assertDoesNotThrow(() -> trainerService.getTraineeNotAssignedActiveTrainers(traineeName));
    }

    @Test
    void testGetTrainerTrainings() {
        // Test data
        TrainerTrainings trainerTrainings = new TrainerTrainings();
        trainerTrainings.setUserName("john.doe");
        trainerTrainings.setStartDate(LocalDate.now());
        trainerTrainings.setEndDate(LocalDate.now());

        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));

        trainer.setTraineeList(List.of(trainee));
        trainer.setSpecialization(new TrainingType());
        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");
        user.setTrainee(trainee);
        user.setTrainer(trainer);
        when(userService.getUser(anyString())).thenReturn(user);
        when(trainingService.getTrainingsForTrainer(any(Trainer.class), any(LocalDate.class), any(LocalDate.class), anyString())).thenReturn(new ArrayList<>());

        // Test
        assertDoesNotThrow(() -> trainerService.getTrainerTrainings(trainerTrainings));
    }

    // Other tests...

}