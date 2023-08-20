package com.epam.gymapp.kafka;

import com.epam.gymapp.dto.report.GymReportDTO;
import com.epam.gymapp.dto.report.NotificationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
class ProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private KafkaTemplate<String, GymReportDTO> gymReportDTOKafkaTemplate;

    @InjectMocks
    NotificationProducer notificationProducer;

    @InjectMocks
    ReportProducer reportProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void sendCredentialNotificationTest() throws JsonProcessingException {
        Mockito.when(kafkaTemplate.send(anyString(),anyString(),anyString())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> notificationProducer.sendCredentialsNotification(new NotificationDTO()));
    }

    @Test
    void sendUpdateNotificationTest() throws JsonProcessingException {
        Mockito.when(kafkaTemplate.send(anyString(),anyString(),anyString())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> notificationProducer.sendUpdateNotification(new NotificationDTO()));
    }

    @Test
    void sendTrainingNotificationTest() throws JsonProcessingException {
        Mockito.when(kafkaTemplate.send(anyString(),anyString(),anyString())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> notificationProducer.sendTrainingNotification(new NotificationDTO()));

    }

    @Test
    void sendGymReportTest() throws JsonProcessingException {
        Mockito.when(gymReportDTOKafkaTemplate.send(anyString(),anyString(),any(GymReportDTO.class))).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> reportProducer.sendGymReport(new GymReportDTO()));
    }

}
