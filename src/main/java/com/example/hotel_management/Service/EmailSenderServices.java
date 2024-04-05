package com.example.hotel_management.Service;

import com.example.hotel_management.Model.VerificationEmailStructure;

public interface EmailSenderServices {
    void sendEmail(String toEmail, VerificationEmailStructure verificationEmailStructure);
    String randomVerificationCode();
}
