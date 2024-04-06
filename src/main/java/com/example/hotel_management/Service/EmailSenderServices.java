package com.example.hotel_management.Service;

import com.example.hotel_management.Model.VerificationEmailStructure;
//Define services for Controller layer action easily
public interface EmailSenderServices {
    /*
    Input:
        A destination email
        A complete VerificationEmailStructure object
    Output: This VerificationEmailStructure will be sent to destination email
     */
    void sendEmail(String toEmail, VerificationEmailStructure verificationEmailStructure);
    //Create a random code
    String randomVerificationCode();
}
