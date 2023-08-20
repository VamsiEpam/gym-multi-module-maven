package com.epam.notificationservice.service;


import com.epam.notificationservice.dto.NotificationDTO;

public interface NotificationService {

    String SEND_CREDENTIALS_MAIL = "sendCredentialsMail";
    String SENT_SUCCESSFULLY = "Sent successfully";
    String EMAIL_SENT_SUCCESSFULLY = "Email sent successfully";
    String FAILED = "Failed";
    String FAILED_TO_SEND_EMAIL = "Failed to send email: ";

    void sendMail(NotificationDTO notificationDTO);
}
