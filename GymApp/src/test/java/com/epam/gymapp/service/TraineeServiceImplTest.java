package com.epam.gymapp.service;

import com.epam.gymapp.customexceptions.TraineeException;
import com.epam.gymapp.customexceptions.UserException;
import com.epam.gymapp.dto.CredentialsDetails;
import com.epam.gymapp.dto.request.TraineeRegistration;
import com.epam.gymapp.dto.request.TraineeTrainingsRequest;
import com.epam.gymapp.dto.request.TraineeUpdateRequest;
import com.epam.gymapp.dto.request.TraineesTrainerUpdateRequest;
import com.epam.gymapp.dto.response.TraineeDetails;
import com.epam.gymapp.dto.response.TrainingResponse;
import com.epam.gymapp.helper.CredentialGenerator;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.User;
import com.epam.gymapp.repository.TraineeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private CredentialGenerator credentialGenerator;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TrainingServiceImpl trainingService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    @Spy
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainee() {
        // Create a TraineeRegistration object
        TraineeRegistration traineeDetails = new TraineeRegistration();
        traineeDetails.setFirstName("John");
        traineeDetails.setLastName("Doe");
        traineeDetails.setEmail("john.doe@example.com");

        // Mocking
        User user = new User();
        user.setIsActive(true);
        CredentialsDetails credentialsDetails = new CredentialsDetails("username", "password");
        when(credentialGenerator.generateCredentials(any(User.class))).thenReturn(credentialsDetails);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Test
        CredentialsDetails result = traineeService.createTrainee(traineeDetails);

        assertEquals(credentialsDetails, result);
        assertEquals(null, user.getUserName());
        assertTrue(user.getIsActive());

        verify(traineeRepository).save(any(Trainee.class));
    }

    @Test
    void testGetTrainee() {
        // Mocking
        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));

        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");
        user.setTrainee(trainee);
        user.setTrainer(trainer);
        when(userService.getUser(anyString())).thenReturn(user);

        // Test
        TraineeDetails result = traineeService.getTrainee("username");

        assertNotNull(result);
        assertEquals("acea", result.getUserName());

    }

    @Test
    void testGetTraineeException() {
        // Mocking
        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));

        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");
        when(userService.getUser(anyString())).thenReturn(user);

        // Test
        assertThrows(UserException.class, () -> traineeService.getTrainee("username"));
    }

    @Test
    void testUpdateTrainee() {
        // Mocking
        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));

        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");
        user.setTrainee(trainee);
        user.setTrainer(trainer);
        when(userService.getUser(anyString())).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(traineeRepository.findByUserId(anyInt())).thenReturn(new Trainee());
        when(traineeRepository.save(any())).thenReturn(new Trainee());

        // Test
        TraineeUpdateRequest traineeUpdateRequest = new TraineeUpdateRequest();
        traineeUpdateRequest.setUserName("sdv");
        TraineeDetails result = traineeService.updateTrainee(traineeUpdateRequest);

        assertNotNull(result);
        assertEquals("acea", result.getUserName());

        verify(traineeRepository).save(any(Trainee.class));
    }

    @Test
    void testUpdateTraineeException() {
        // Mocking
        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));

        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");
        when(userService.getUser(anyString())).thenReturn(user);

        // Test
        TraineeUpdateRequest traineeUpdateRequest = new TraineeUpdateRequest();
        traineeUpdateRequest.setUserName("sdv");
        assertThrows(TraineeException.class,() -> traineeService.updateTrainee(traineeUpdateRequest));

    }

    @Test
    void testUpdateTraineesTrainerList() {
        // Mocking
        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));

        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");
        user.setTrainee(trainee);
        user.setTrainer(trainer);

        when(userService.getUser(anyString())).thenReturn(user);
        when(traineeRepository.findByUserId(anyInt())).thenReturn(new Trainee());
        when(traineeRepository.save(any(Trainee.class))).thenReturn(new Trainee());
        when(traineeService.getTrainee(anyString())).thenReturn(new TraineeDetails());
        TraineesTrainerUpdateRequest traineesTrainerUpdateRequest = new TraineesTrainerUpdateRequest();
        traineesTrainerUpdateRequest.setTraineeUserName("aeacac");
        traineesTrainerUpdateRequest.setTrainersUserName(List.of("aceo"));
        // Test
        TraineeDetails result = traineeService.updateTraineesTrainerList(traineesTrainerUpdateRequest);

        assertNotNull(result);
        assertEquals(null, result.getUserName());

        verify(traineeRepository).save(any(Trainee.class));
    }

    @Test
    void testUpdateTraineesTrainerListException() {
        // Mocking
        User user = new User();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainee.setUser(user);
        trainee.setAddress("Aecea");
        trainer.setUser(user);
        trainee.setTrainerList(List.of(trainer));

        user.setIsActive(true);
        user.setFirstName("qeefwe");
        user.setLastName("afaef");
        user.setUserName("acea");

        when(userService.getUser(anyString())).thenReturn(user);
       TraineesTrainerUpdateRequest traineesTrainerUpdateRequest = new TraineesTrainerUpdateRequest();
        traineesTrainerUpdateRequest.setTraineeUserName("aeacac");
        traineesTrainerUpdateRequest.setTrainersUserName(List.of("aceo"));
        // Test
        assertThrows(TraineeException.class, () -> traineeService.updateTraineesTrainerList(traineesTrainerUpdateRequest));

    }




    @Test
    void testRemoveTrainee() {
        // Mocking
        User user = new User();
        Trainee trainee = new Trainee();
        trainee.setTrainerList(new ArrayList<>());
        user.setTrainee(trainee);
        when(userService.getUser(anyString())).thenReturn(user);

        // Test
        assertDoesNotThrow(() -> traineeService.removeTrainee("username"));

        verify(userService).removeUser(any(User.class));
    }

    @Test
    void testGetTraineeTrainings() {
        // Mocking
        User user = new User();
        Trainee trainee = new Trainee();
        user.setTrainee(trainee);
        when(userService.getUser(anyString())).thenReturn(user);
        when(trainingService.getTrainingsForTrainee(any(Trainee.class), any(), any(), anyInt())).thenReturn(new ArrayList<>());
        TraineeTrainingsRequest traineeTrainingsRequest = new TraineeTrainingsRequest();
        traineeTrainingsRequest.setUserName("aceaf");

        // Test
        List<TrainingResponse> result = traineeService.getTraineeTrainings(traineeTrainingsRequest);

        assertEquals(0, result.size());
    }
}
