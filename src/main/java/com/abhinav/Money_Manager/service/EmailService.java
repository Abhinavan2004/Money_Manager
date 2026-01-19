package com.abhinav.Money_Manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage simpleMessage = new SimpleMailMessage();
            simpleMessage.setFrom(fromEmail);
            simpleMessage.setTo(to);
            simpleMessage.setSubject(subject);
            simpleMessage.setText(body);
            mailSender.send(simpleMessage);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }
}
