package com.epam.notificationservice.service;

import com.epam.notificationservice.customexception.NotificationException;
import com.epam.notificationservice.dto.NotificationDTO;
import com.epam.notificationservice.repository.EmailRepository;
import com.epam.notificationservice.model.Mail;
import com.epam.notificationservice.model.StringConstants;
import com.epam.notificationservice.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{


    private final JavaMailSender javaMailSender;

    private final EmailRepository mailRepository;


    @Override
    public void sendMail(NotificationDTO notificationDTO) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(), SEND_CREDENTIALS_MAIL,this.getClass().getName(),notificationDTO);

        Mail mail = ValueMapper.convertNotificationToMail(notificationDTO);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getRecipients().toArray(new String[0]));
        mailMessage.setFrom("191fa04354@gmail.com");
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getBody());
        try {
            javaMailSender.send(mailMessage);
            mail.setStatus(SENT_SUCCESSFULLY);
            mail.setRemarks(EMAIL_SENT_SUCCESSFULLY);
        } catch ( MailException ex) {
            mail.setStatus(FAILED);
            mail.setRemarks(FAILED_TO_SEND_EMAIL + ex.getMessage());
        }
        mailRepository.save(mail);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(), SEND_CREDENTIALS_MAIL);
    }


}
