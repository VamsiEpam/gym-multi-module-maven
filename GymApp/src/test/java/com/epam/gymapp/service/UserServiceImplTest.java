package com.epam.gymapp.service;

import com.epam.gymapp.customexceptions.UserException;
import com.epam.gymapp.dto.CredentialsDetails;
import com.epam.gymapp.dto.request.ModifyCredentialsRequest;
import com.epam.gymapp.model.User;
import com.epam.gymapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateCredentials() {
        // Mocking
        ModifyCredentialsRequest credentialsDetails = new ModifyCredentialsRequest();
        credentialsDetails.setUserName("testUser");
        credentialsDetails.setOldPassword("oldPass");
        credentialsDetails.setNewPassword("Vamsi.2002@");

        User user = new User();
        user.setUserName("testUser");
        user.setPassword("oldPass");

        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Test
        assertDoesNotThrow(() -> {
            userService.updateCredentials(credentialsDetails);
        });

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateCredentialsSamePassword() {
        // Test data
        ModifyCredentialsRequest credentialsDetails = new ModifyCredentialsRequest();
        credentialsDetails.setUserName("testUser");
        credentialsDetails.setOldPassword("oldPass");
        credentialsDetails.setNewPassword("oldPass");

        // Test and assert exception
        assertThrows(UserException.class, () -> {
            userService.updateCredentials(credentialsDetails);
        });
    }

    @Test
    void testUpdateCredentialsPasswordRequirementsNotMet() {
        // Test data
        ModifyCredentialsRequest credentialsDetails = new ModifyCredentialsRequest();
        credentialsDetails.setUserName("testUser");
        credentialsDetails.setOldPassword("oldPass");
        credentialsDetails.setNewPassword("abc");

        // Test and assert exception
        assertThrows(UserException.class, () -> {
            userService.updateCredentials(credentialsDetails);
        });
    }

    @Test
    void testUpdateCredentialsOldPasswordMismatch() {
        // Test data
        ModifyCredentialsRequest credentialsDetails = new ModifyCredentialsRequest();
        credentialsDetails.setUserName("testUser");
        credentialsDetails.setOldPassword("oldPass");
        credentialsDetails.setNewPassword("newPass");

        User user = new User();
        user.setUserName("testUser");
        user.setPassword("wrongPass");

        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));

        // Test and assert exception
        assertThrows(UserException.class, () -> {
            userService.updateCredentials(credentialsDetails);
        });
    }

    @Test
    void testGetUser() {
        // Mocking
        User user = new User();
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));

        // Test
        User result = userService.getUser("testUser");

        assertNotNull(result);
    }

    @Test
    void testGetUserNotFound() {
        // Mocking
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        // Test and assert exception
        assertThrows(UserException.class, () -> {
            userService.getUser("testUser");
        });
    }

    @Test
    void testRemoveUser() {
        // Test data
        User user = new User();

        // Test
        assertDoesNotThrow(() -> {
            userService.removeUser(user);
        });

        verify(userRepository).delete(user);
    }

    @Test
    void testLoginSuccessful() {
        // Test data
        CredentialsDetails credentialsDetails = new CredentialsDetails();
        credentialsDetails.setUserName("testUser");
        credentialsDetails.setPassword("testPass");

        User user = new User();
        user.setUserName("testUser");
        user.setPassword("testPass");

        // Mocking
        when(userRepository.findByUserName(anyString())).thenReturn(java.util.Optional.of(user));

        // Test
        assertDoesNotThrow(() -> userService.login(credentialsDetails));
    }

    @Test
    void testLoginWrongPassword() {
        // Test data
        CredentialsDetails credentialsDetails = new CredentialsDetails();
        credentialsDetails.setUserName("testUser");
        credentialsDetails.setPassword("wrongPass");

        User user = new User();
        user.setUserName("testUser");
        user.setPassword("testPass");

        // Mocking
        when(userRepository.findByUserName(anyString())).thenReturn(java.util.Optional.of(user));

        // Test and assert exception
        UserException exception = assertThrows(UserException.class, () -> userService.login(credentialsDetails));
        assertEquals("wrong password", exception.getMessage());
    }

    @Test
    void testLoginUserNotFound() {
        // Test data
        CredentialsDetails credentialsDetails = new CredentialsDetails();
        credentialsDetails.setUserName("nonExistentUser");
        credentialsDetails.setPassword("testPass");

        // Mocking
        when(userRepository.findByUserName(anyString())).thenReturn(java.util.Optional.empty());

        // Test and assert exception
        UserException exception = assertThrows(UserException.class, () -> userService.login(credentialsDetails));
        assertEquals("No Account with that username", exception.getMessage());
    }

    @Test
    void testSaveUser()
    {
        when(userRepository.save(any())).thenReturn(new User());

        assertDoesNotThrow(() -> userService.saveUser(new User()));

        verify(userRepository).save(any(User.class));
    }

}
