package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.VerificationEmailStructure;
import com.example.hotel_management.Service.EmailSenderServices;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
//Implement defined interface
@Service
public class EmailSenderServicesImpl implements EmailSenderServices {
    //Define some attribute
    private final JavaMailSender sender;
    //Dependency Injection
    @Autowired
    public EmailSenderServicesImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    //Send email
    //Embed image into this email
    //If error -> out
    @Override
    public void sendEmail(String toEmail, VerificationEmailStructure verificationEmailStructure) {
        MimeMessage message = sender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(verificationEmailStructure.getSubject());
            helper.setSentDate(new Date());

            helper.setText(verificationEmailStructure.getMessage(), true);
            helper.addInline("logo", new ClassPathResource("/static/images/logo.png"));

            sender.send(message);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    //Random verify code (6 letters)
    @Override
    public String randomVerificationCode() {
        int length = 4;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            code.append(randomChar);
        }
        return code.toString();
    }
}
