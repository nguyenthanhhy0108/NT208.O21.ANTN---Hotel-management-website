package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.VerificationEmailStructure;
import com.example.hotel_management.Service.EmailSenderServices;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void sendEmail(String toEmail,
                          VerificationEmailStructure verificationEmailStructure) {
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

    @Override
    public boolean checkVerificationCode(VerificationEmailStructure verificationEmailStructure,
                                         String rawCode) {
        if(verificationEmailStructure
                .getVerificationCode()
                .equals(rawCode))
            return true;
        else
            return false;
    }

    //Check verification code expire
    //Get current time and plus 30 minutes, compare to sent time
    @Override
    public boolean checkExpiredVerificationCode(VerificationEmailStructure verificationEmailStructure) {
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(verificationEmailStructure.getSentTime().plusMinutes(30))){
            return true;
        }
        return false;
    }
    //Initialize email attributes
    @Override
    public void prepareEmail(VerificationEmailStructure verificationEmailStructure) {
        verificationEmailStructure.setVerificationCode(this.randomVerificationCode());
        verificationEmailStructure.replaceCode();
        verificationEmailStructure.setSentTime(LocalDateTime.now());
    }
}
