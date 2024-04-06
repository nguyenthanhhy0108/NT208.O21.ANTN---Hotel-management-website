package com.example.hotel_management.Model;

import lombok.Data;

import java.time.LocalDateTime;
//This class define an email structure which will be sent for user when they need
//A function for replacing ****** code in HTML with a real code
@Data
public class VerificationEmailStructure {
    private String subject;
    private String message;
    private String verificationCode;
    private LocalDateTime sentTime;

    public VerificationEmailStructure() {
        this.subject = "NT208 verification code";
        this.message = "<html><body>"
                + "<div style=\"max-width: 400px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; background-color: #f9f9f9;\">"
                + "<img src='cid:logo' alt=\"Alternative text\" style=\"max-width: 200px; max-height: 200px;\">"
                + "<hr><h1 style=\"text-align: left; margin-bottom: 20px;\">Verification Code</h1><br>"
                + "<p>Hello.</p>"
                + "<p>To reset your password, please verify that is you by entering the code.</p>"
                + "<p>Verification code expires in 30 minutes</p><br>"
                + "<p style=\"font-size: 18px; text-align: center;\">Your verification code is:</p>"
                + "<p style=\"font-size: 36px; text-align: center; font-weight: bold; font-family: 'Roboto Mono', monospace; color: #f5810b;\">******</p><br>"
                + "<p>Code expired? Try it again to receive a new code.</p>"
                + "<p>If you did not request this code, please ignore this. SE automatic mail cannot be created.</p>"
                + "<p>If you experience any issues regarding email verification or account creation, please contact our: <a href=\"http://facebook.com\">NT208support.com</a></p><hr>"
                + "<p style=\"font-family: Arial, sans-serif; font-size: 10px; color: #999;\">"
                + "This message was sent from an unmonitored email address. Please do not reply to this message.</p>"
                + "<p style=\"font-family: Arial, sans-serif; font-size: 10px; color: #999;\">"
                + "© 2024 <span style=\"font-weight: bold;\">NT208 Application</span>, Inc. All rights reserved.</p></div>"
                + "</body></html>";
    }
    //This function is used for replacing ****** code in HTML with a real code
    public void replaceCode(){
        this.message = this.message.replace("******", this.verificationCode);
    }
}
