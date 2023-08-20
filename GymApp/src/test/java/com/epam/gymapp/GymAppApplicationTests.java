package com.epam.gymapp;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest
class GymAppApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
