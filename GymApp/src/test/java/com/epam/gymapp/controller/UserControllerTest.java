package com.epam.gymapp.controller;

import com.epam.gymapp.GymAppApplication;
import com.epam.gymapp.dto.CredentialsDetails;
import com.epam.gymapp.dto.request.ModifyCredentialsRequest;
import com.epam.gymapp.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = GymAppApplication.class)
  class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;


    @Test
    void testInsertBook()  {
       Mockito.doNothing().when(userService).login(any());
       userController.login(new CredentialsDetails());
        Mockito.verify(userService).login(any());
    }

    @Test
    void changeLoginTest(){
        Mockito.doNothing().when(userService).updateCredentials(any());
        userController.changeLogin(new ModifyCredentialsRequest());
        Mockito.verify(userService).updateCredentials(any());
    }
}
