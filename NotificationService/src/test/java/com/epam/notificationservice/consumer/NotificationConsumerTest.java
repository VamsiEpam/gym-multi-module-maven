package com.epam.notificationservice.consumer;

import com.epam.notificationservice.customexception.NotificationException;
import com.epam.notificationservice.dto.NotificationDTO;
import com.epam.notificationservice.kafka.NotificationConsumer;
import com.epam.notificationservice.service.NotificationService;
import com.epam.notificationservice.service.NotificationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NotificationConsumerTest {

    @InjectMocks
    private NotificationConsumer notificationConsumer;


    @Mock
    private NotificationServiceImpl notificationService;


    @Test
    void testReadNotificationFromBroker() throws JsonProcessingException, NotificationException {
        List<String> toEmails = new ArrayList<>();
        toEmails.add("vk@epam.com");
        Map<String,String> mailBody = new HashMap<>();
        mailBody.put("Username","vamsi");
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setBodyParameters(mailBody);
        notificationDTO.setEmailType("update");
        notificationDTO.setToEmails(toEmails);
        String readData =new ObjectMapper().writeValueAsString(notificationDTO);

//        ArgumentCaptor<NotificationDTO> notificationDTOCaptor = ArgumentCaptor.forClass(NotificationDTO.class);
        doNothing().when(notificationService).sendMail(any());

        notificationConsumer.getNotificationDTO(readData);

        verify(notificationService, times(1)).sendMail(any(NotificationDTO.class));

    }
}
