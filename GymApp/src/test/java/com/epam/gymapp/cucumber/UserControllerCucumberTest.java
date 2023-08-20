package com.epam.gymapp.cucumber;

import com.epam.gymapp.controller.UserController;
import com.epam.gymapp.dto.CredentialsDetails;
import com.epam.gymapp.dto.request.ModifyCredentialsRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
public class UserControllerCucumberTest {

    @Autowired
    UserController userController;


    ResponseEntity<HttpStatus> responseEntity = null;


    @Given(": Execute login Method")
    public void execute_login_method() {
        CredentialsDetails credentialsDetails = new CredentialsDetails();
        credentialsDetails.setUserName("gauri_k_6969");
        credentialsDetails.setPassword("Vamsi.2002");
        responseEntity = userController.login(credentialsDetails);
    }
    @Then(": http ok status returned")
    public void http_ok_status_returned() {
        assertEquals(new ResponseEntity<>(HttpStatus.OK),responseEntity);
    }

    @Given(": Execute update method")
    public void execute_update_method() {
        ModifyCredentialsRequest modifyCredentialsRequest = new ModifyCredentialsRequest();
        modifyCredentialsRequest.setUserName("gauri_k_6969");
        modifyCredentialsRequest.setOldPassword("Vamsi.2002");
        modifyCredentialsRequest.setNewPassword("Vamsi@2002");
        responseEntity = userController.changeLogin(modifyCredentialsRequest);
    }


}
