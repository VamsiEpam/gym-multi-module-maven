package com.epam.notificationservice.kafka;


import com.epam.notificationservice.dto.NotificationDTO;
import com.epam.notificationservice.model.StringConstants;
import com.epam.notificationservice.service.NotificationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationServiceImpl notificationService;


    @KafkaListener(topics = "notification",groupId = "mail")
    public void getNotificationDTO(String message) throws JsonProcessingException {
        log.info(StringConstants.START_CONTROLLER_METHOD.getValue(), "getNotificationDTO", this.getClass().getName(), message);
        NotificationDTO notificationDTO = new ObjectMapper().readValue(message, NotificationDTO.class);
        notificationService.sendMail(notificationDTO);
        log.info(StringConstants.EXIT_CONTROLLER_METHOD.getValue(), "getNotificationDTO");
    }

}
