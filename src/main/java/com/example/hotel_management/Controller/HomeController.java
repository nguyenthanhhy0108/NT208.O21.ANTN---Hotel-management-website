package com.example.hotel_management.Controller;

import com.example.hotel_management.Service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    //Define some services
    private final UserServices userServices;

    /**
     * Dependency Injection
     * @param userServices: UserServices object
     */
    @Autowired
    public HomeController(UserServices userServices) {
        this.userServices = userServices;
    }

    /**
     * Get first page
     * @param request: HttpServletRequest object to get request parameter
     * @return
     * Redirect first_page.html
     */
    @GetMapping("/first-page")
    String FirstPage(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            return "first_page";
        }
        else{
            //Set attribute name in session
            HttpSession session = request.getSession();
            session.setAttribute("name", userServices.findByUsername(authentication.getName())
                    .get(0)
                    .getUserDetails()
                    .getName());
        }
        return "first_page";
    }

    /**
     * Get home page
     * @return
     * Redirect home.html
     */
    @GetMapping("/home")
    public String HomePage(){
        return "home";
    }
}
