package com.example.hotel_management.Controller;

import com.example.hotel_management.Config.SecurityConfig;
import com.example.hotel_management.Model.Authorities;
import com.example.hotel_management.Model.Users;
import com.example.hotel_management.Model.UserDetails;
import com.example.hotel_management.Service.AuthoritiesServices;
import com.example.hotel_management.Service.UserDetailsServices;
import com.example.hotel_management.Service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RegisterController {
    //Define and initialize some attribute
    private final UserServices userServices;
    private final UserDetailsServices userDetailsServices;
    private final AuthoritiesServices authoritiesServices;
    private final PasswordEncoder encoder = SecurityConfig.passwordEncoder();
    //Dependency Injection
    @Autowired
    public RegisterController(UserServices userServices, UserDetailsServices userDetailsServices, AuthoritiesServices authoritiesServices) {
        this.userServices = userServices;
        this.userDetailsServices = userDetailsServices;
        this.authoritiesServices = authoritiesServices;
    }
    //Return register page which is sign_up.html
    @GetMapping("/register")
    String RegisterPage(){
        return "sign_up";
    }
    //Process when user create a new account
    //Return some error if exist
    //If create successfully redirect forward login page which is sign_in.html
    @PostMapping("/register")
    public String Register(Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String username = request.getParameter("email");
        String password = request.getParameter("password");

        List<Users> usersList = userServices.findByUsername(username);

        model.addAttribute("name", name);
        model.addAttribute("email", username);

        //Check used email
        if(userServices.checkUserExistByUsername(username)){
            model.addAttribute("email_in_used", true);
            return "sign_up";
        }
        //Otherwise -> save -> return login page
        else{
            Users newUsers = userServices.save(new Users(username, encoder.encode(password), 1));
            Authorities authorities = authoritiesServices.save(new Authorities(username, "ROLE_USER"));
            UserDetails userDetails = userDetailsServices.save(new UserDetails(username, name, "", "", ""));
        }

        HttpSession session = request.getSession();
        model.addAttribute("create_account_successfully", true);
        session.removeAttribute("password_wrong");
        session.removeAttribute("username_not_exist");
        return "sign_in";
    }
}
