package com.epam.gymapp.service;

import com.epam.gymapp.customexceptions.TraineeException;
import com.epam.gymapp.dto.TrainingOverallDTO;
import com.epam.gymapp.dto.request.TrainingRequest;
import com.epam.gymapp.model.*;
import com.epam.gymapp.repository.TrainingRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TrainingTypeServiceImpl trainingTypeService;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTraining() {
        User user  = new User();
        // Mocking
        Trainer trainer = new Trainer();
        Trainee trainee = new Trainee();


        trainee.setTrainerList(List.of(trainer));
        trainer.setTraineeList(List.of(trainee));


        trainer.setUser(user);
        trainee.setUser(user);

        user.setTrainer(trainer);
        user.setTrainee(trainee);
        user.setIsActive(true);

        when(userService.getUser(anyString())).thenReturn(user);
        when(userService.getUser(anyString())).thenReturn(user);

        // Create a TrainingRequest object
        TrainingRequest trainingDetails = new TrainingRequest();
        trainingDetails.setTrainerUserName("trainerUsername");
        trainingDetails.setTraineeUserName("traineeUsername");
        trainingDetails.setTrainingName("Training Name");
        trainingDetails.setTrainingDate(LocalDate.now());
        trainingDetails.setTrainingDuration(60);

        // Test
        TrainingOverallDTO result = trainingService.addTraining(trainingDetails);

        assertNotNull(result);
        assertNotNull(result.getGymReportDTO());
        assertNotNull(result.getTrainingMailDTO());

        verify(trainingRepository).save(any(Training.class));
    }
    @Test
    void testAddTrainingException() {
        User user  = new User();
        // Mocking
        Trainer trainer = new Trainer();
        Trainee trainee = new Trainee();


        trainee.setTrainerList(new ArrayList<>());
        trainer.setTraineeList(new ArrayList<>());


        trainer.setUser(user);
        trainee.setUser(user);

        user.setTrainer(trainer);
        user.setTrainee(trainee);
        user.setIsActive(true);

        when(userService.getUser(anyString())).thenReturn(user);
        when(userService.getUser(anyString())).thenReturn(user);

        // Create a TrainingRequest object
        TrainingRequest trainingDetails = new TrainingRequest();
        trainingDetails.setTrainerUserName("trainerUsername");
        trainingDetails.setTraineeUserName("traineeUsername");
        trainingDetails.setTrainingName("Training Name");
        trainingDetails.setTrainingDate(LocalDate.now());
        trainingDetails.setTrainingDuration(60);

        // Test
        assertThrows(TraineeException.class,() ->  trainingService.addTraining(trainingDetails));


    }

    @Test
    void testGetTrainingsForTrainee() {
        // Mocking
        Trainee trainee = new Trainee();
        when(userService.getUser(anyString())).thenReturn(new User());
        when(trainingTypeService.getTrainingType(anyInt())).thenReturn(new TrainingType());

        // Test
        List<Training> result = trainingService.getTrainingsForTrainee(trainee, LocalDate.now(), LocalDate.now(), 1);

        assertNotNull(result);
    }

    @Test
    void testGetTrainingsForTrainer() {
        // Mocking
        Trainer trainer = new Trainer();
        when(userService.getUser(anyString())).thenReturn(new User());

        // Test
        List<Training> result = trainingService.getTrainingsForTrainer(trainer, LocalDate.now(), LocalDate.now(), "traineeUsername");

        assertNotNull(result);
    }

    @Test
    void testGetTraining() {

        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");
        user.setTrainee(trainee);
        user.setTrainer(trainer);
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));


        Training training = new Training();
        training.setDate(LocalDate.of(12,1,1));
        training.setName("easfa");
        training.setTrainingType(new TrainingType());
        training.setDuration(12);
        training.setTrainer(trainer);
        training.setTrainee(trainee);

        // Mocking
        when(trainingRepository.findByName(anyString())).thenReturn(training);

        // Test
        TrainingRequest result = trainingService.getTraining("Training Name");

        assertNotNull(result);
    }

}