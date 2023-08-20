package com.epam.notificationservice.service;

import com.epam.notificationservice.customexception.NotificationException;
import com.epam.notificationservice.dto.NotificationDTO;
import com.epam.notificationservice.repository.EmailRepository;
import com.epam.notificationservice.model.Mail;
import com.epam.notificationservice.util.ValueMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotificationServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private EmailRepository mailRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMail_Success() {
        List<String> toEmails = new ArrayList<>();
        toEmails.add("vk@epam.com");
        Map<String,String> mailBody = new HashMap<>();
        mailBody.put("Username","vamsi");
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setBodyParameters(mailBody);
        notificationDTO.setEmailType("update");
        notificationDTO.setToEmails(toEmails);

        Mail mail = ValueMapper.convertNotificationToMail(notificationDTO);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getRecipients().toArray(new String[0]));
        mailMessage.setFrom("191fa04354@gmail.com");
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getBody());


        Mockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        Mockito.when(mailRepository.save(any(Mail.class))).thenReturn(Mail.builder().build());

        notificationService.sendMail(notificationDTO);

        verify(javaMailSender).send(mailMessage);
        verify(mailRepository).save(Mockito.any(Mail.class));
    }

    @Test
    public void testSendMail_Failure() {
        List<String> toEmails = new ArrayList<>();
        toEmails.add("vk@epam.com");
        Map<String,String> mailBody = new HashMap<>();
        mailBody.put("Username","vamsi");
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setBodyParameters(mailBody);
        notificationDTO.setEmailType("update");
        notificationDTO.setToEmails(toEmails);

        Mail mail = ValueMapper.convertNotificationToMail(notificationDTO);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getRecipients().toArray(new String[0]));
        mailMessage.setFrom("191fa04354@gmail.com");
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getBody());

        Mockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        notificationService.sendMail(notificationDTO);

        verify(javaMailSender).send(mailMessage);
        verify(mailRepository).save(Mockito.any(Mail.class));
    }
}
