package com.example.hotel_management.Security;
import com.example.hotel_management.Config.SecurityConfig;
import com.example.hotel_management.Model.Users;
import com.example.hotel_management.Service.UserServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    //Identify internal attribute

    private final UserServices UserServices;

    /**
     * Dependency Injection
     * @param userServices: UserServices object
     */
    @Autowired
    public CustomAuthenticationFailureHandler(UserServices userServices) {
        UserServices = userServices;
    }

    //Global PasswordEncoder from SecurityConfig
    private final PasswordEncoder encoder = SecurityConfig.passwordEncoder();

    /**
     * Handle failure when login
     * @param request: HttpServletRequest object
     * @param response: HttpServletResponse object
     * @param exception: AuthenticationException object
     * @throws IOException
     * Exception
     * @throws ServletException
     * Exception
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        //Handle login error
        //Get some parameter form login form
        //Set username into login form to help end user
        //Check username and password in database and set error message if it existed
        super.onAuthenticationFailure(request, response, exception);

        String username = request.getParameter("username");
        HttpSession session = request.getSession();
        session.setAttribute("username", username);

        session.setAttribute("username_not_exist", false);
        session.setAttribute("password_wrong", false);

        List<Users> accounts = UserServices.findByUsername(username);
        if(accounts.isEmpty()){
            session.setAttribute("username_not_exist", true);
            session.removeAttribute("password_wrong");
        }
        else{
            String UserPassword = accounts.get(0).getPassword();
            if(!encoder.matches(request.getParameter("password"), UserPassword)){
                session.setAttribute("password_wrong", true);
                session.removeAttribute("username_not_exist");
            }
        }
    }
}