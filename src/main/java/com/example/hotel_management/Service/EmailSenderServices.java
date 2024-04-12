package com.example.hotel_management.Service;

import com.example.hotel_management.Model.VerificationEmailStructure;
//Define services for Controller layer action easily
public interface EmailSenderServices {

    /**
     * This service is used to send a mail to destination email
     * @param toEmail: A destination email
     * @param verificationEmailStructure: A complete VerificationEmailStructure object
     */
    void sendEmail(String toEmail, VerificationEmailStructure verificationEmailStructure);

    /**
     * This service is used to create a random code
     * @return
     * Complete verification code
     */
    String randomVerificationCode();

    /**
     * This service is used to check verification code
     * @param verificationEmailStructure: VerificationEmailStructure object
     * @param rawCode: A raw code (For instance: abc123!@)
     * @return
     * A boolean
     */
    boolean checkVerificationCode(VerificationEmailStructure verificationEmailStructure, String rawCode);

    /**
     * This service is used to check verification code expired
     * @param verificationEmailStructure: VerificationEmailStructure object
     * @return
     * A boolean
     */
    boolean checkExpiredVerificationCode(VerificationEmailStructure verificationEmailStructure);

    /**
     * This service is used to prepare a mail
     * @param verificationEmailStructure: VerificationEmailStructure object
     */
    void prepareEmail(VerificationEmailStructure verificationEmailStructure);
}
