package com.epam.gymapp.cucumber;

import com.epam.gymapp.controller.TraineeController;
import com.epam.gymapp.dto.CredentialsDetails;
import com.epam.gymapp.dto.request.TraineeRegistration;
import com.epam.gymapp.dto.response.TraineeDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public class TraineeControllerCucumberTest {

    @Autowired
    TraineeController traineeController;

    ResponseEntity<CredentialsDetails> credentialsDetailsResponseEntity = null;

    ResponseEntity<Void> response = null;


    @Given(": Execute registerTrainee")
    public void execute_register_trainee() throws JsonProcessingException {
        TraineeRegistration traineeRegistration = new TraineeRegistration();
        traineeRegistration.setFirstName("vamsi");
        traineeRegistration.setLastName("krishna");
        traineeRegistration.setAddress("etukuru");
        traineeRegistration.setEmail("vk1@gmail.com");
        traineeRegistration.setDateOfBirth(LocalDate.of(1,1,12));

        credentialsDetailsResponseEntity = traineeController.registerTrainee(traineeRegistration);

    }
    @Then(": CredentialDetails is returned")
    public void credential_details_is_returned() {

    }




    @Given(": Execute deleteTrainee")
    public void execute_delete_trainee() {
        response = traineeController.deleteTrainee("vamsi_krishna_9064");
    }
    @Then(": Http No content status is returned")
    public void http_no_content_status_is_returned() {
        Assertions.assertEquals(response.getStatusCode(),HttpStatus.NO_CONTENT);
    }

}
