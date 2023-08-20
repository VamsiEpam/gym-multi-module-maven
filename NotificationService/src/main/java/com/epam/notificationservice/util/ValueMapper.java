package com.epam.notificationservice.util;


import com.epam.notificationservice.dto.NotificationDTO;
import com.epam.notificationservice.model.Mail;
import com.epam.notificationservice.model.StringConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ValueMapper {

    private ValueMapper() {

    }


    public static final String WITH_REGARDS_EPAM_GYM_APPLICATION = "With Regards \nEPAM Gym Application";
    public static final String NEW_LINE = "\n\n\n";


    public static String getBody(Map<String, String> bodyParameters) {
        return bodyParameters.entrySet().stream().map(entry -> entry.getKey() + " : " + entry.getValue()).collect(Collectors.joining("\n"));
    }

    public static Mail convertNotificationToMail(NotificationDTO notificationDTO) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(), "convertCredentialsToMail", ValueMapper.class, notificationDTO);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(), "convertCredentialsToMail");
        return Mail.builder()
                .subject(getSubject(notificationDTO.getEmailType()))
                .body(ValueMapper.getBody(notificationDTO.getBodyParameters()) + NEW_LINE + WITH_REGARDS_EPAM_GYM_APPLICATION)
                .recipients(notificationDTO.getToEmails())
                .build();
    }

    private static String getSubject(String type) {
        return switch (type) {
            case ("update") -> StringConstants.UPDATE_MAIL.getValue();
            case ("register") -> StringConstants.REGISTER_MAIL.getValue();
            case ("training") -> StringConstants.TRAINING_MAIL.getValue();
            default -> "Gym Application";
        };
    }
}
