package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.VerificationEmailStructure;
import com.example.hotel_management.Service.EmailSenderServices;
import com.example.hotel_management.Service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ForgetPasswordController {
    //Define and initialize some attribute
    private final UserServices userServices;
    private VerificationEmailStructure verificationEmailStructure = new VerificationEmailStructure();
    private final EmailSenderServices emailSenderServices;

    private String typedEmail;
    //Dependency injection
    @Autowired
    public ForgetPasswordController(UserServices userServices,
                                    EmailSenderServices emailSenderServices) {
        this.userServices = userServices;
        this.emailSenderServices = emailSenderServices;
    }

    //Add method to get forget_password.html
    @GetMapping("/forget-password")
    public String forgetPasswordPage(){
        return "forget_password";
    }

    /*
    Add method to process when users forgot their password
    - Method: POST
    - Action /forget-password

    - 3 form
        + Form 1: Controlling when user provide their email
            - Check exist email
            - Initialize email structure
        + Form 2: Checking when user provide verify code
            - Check code (expired, match)
        + Form 3: Reset password
            - Check password overlap
            - If not -> save -> return login page
    - Return some error notify parallelly.
     */
    /*
        Input:
            RequestParam: formId to know which form is received
            ...
        Output:
            ResponseEntity: String - Object
     */
    @PostMapping("/forget-password")
    public ResponseEntity<Map<String, Object>> process(@RequestParam("formId") String formId,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       Model model){

        Map<String, Object> resposeMap = new HashMap<>();
        //Form 1
        if ("form1".equals(formId)){
            typedEmail = request.getParameter("email");
            if(!userServices.checkUserExistByUsername(typedEmail)){
                resposeMap.put("Fail", true);
                resposeMap.put("notExist", true);
            }
            else {
                this.verificationEmailStructure = new VerificationEmailStructure();

                resposeMap.put("Fail", false);
                resposeMap.put("notExist", false);

                emailSenderServices.prepareEmail(this.verificationEmailStructure);
                emailSenderServices.sendEmail(typedEmail, this.verificationEmailStructure);
            }
            HttpSession session = request.getSession();
            session.setAttribute("email", typedEmail);

            resposeMap.put("email", typedEmail);

            response.setContentType("text/html");

            return new ResponseEntity<>(resposeMap, HttpStatus.OK);
        }
        //Form2
        if ("form2".equals(formId)){
            String providedCode = request.getParameter("char1")
                    + request.getParameter("char2")
                    + request.getParameter("char3")
                    + request.getParameter("char4");

            //Check expired code and response
            if(emailSenderServices.checkExpiredVerificationCode(this.verificationEmailStructure)){
                resposeMap.put("expiredCode", true);
                resposeMap.put("Fail", true);
                return new ResponseEntity<>(resposeMap, HttpStatus.OK);
            }
            else{
                //Check code (true, false)
                if(!emailSenderServices.checkVerificationCode(this.verificationEmailStructure, providedCode)){
                    resposeMap.put("wrongCode", true);
                    resposeMap.put("Fail", true);
                    return new ResponseEntity<>(resposeMap, HttpStatus.OK);
                }
                else {
                    resposeMap.put("Fail", false);
                    return new ResponseEntity<>(resposeMap, HttpStatus.OK);
                }
            }
        }
        //Form3
        if ("form3".equals(formId)){
            String typedPassword = request.getParameter("password");
            if(userServices.comparePasswordByUsername(this.typedEmail, typedPassword)){
                resposeMap.put("Fail", true);
                resposeMap.put("overlapped", true);
                return new ResponseEntity<>(resposeMap, HttpStatus.OK);
            }
            else {
                userServices.updatePasswordByUsername(this.typedEmail, typedPassword);
                resposeMap.put("successful", true);
                resposeMap.put("Fail", false);
                return new ResponseEntity<>(resposeMap, HttpStatus.OK);
            }
        }

        return null;
    }
}
