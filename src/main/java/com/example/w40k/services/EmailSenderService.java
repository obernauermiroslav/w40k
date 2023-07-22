package com.example.w40k.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String email, String activationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("warhammeradeptus.com");
        message.setTo(email);
        message.setSubject("W40K - Account Activation");
        message.setText("Please click the following link to activate your account: " + generateActivationLink(activationCode));
        mailSender.send(message);
    }

    private String generateActivationLink(String activationCode) {
        String url = "http://localhost:8080/activate?activationCode=" + activationCode;
        return url;
    }
}