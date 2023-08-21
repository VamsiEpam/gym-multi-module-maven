package com.epam.gymapp.cucumber;

import com.epam.gymapp.controller.TrainingTypeController;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class TrainingTypeControllerCucumberTest {
    @Autowired
    TrainingTypeController trainingTypeController;

    ResponseEntity<Void> response = null;

    @When(": Execute addTrainingTypeName Method")
    public void execute_add_training_type_name_method() {
        response = trainingTypeController.addTrainingTypeName("Yoga");
    }

    @Then(": http Created status is returned.")
    public void http_created_status_is_returned() {
        Assertions.assertNotNull(response);
    }
}
