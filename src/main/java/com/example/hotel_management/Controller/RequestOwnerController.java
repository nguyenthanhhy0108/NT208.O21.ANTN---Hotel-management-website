package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Authorities;
import com.example.hotel_management.Model.RequestOwner;
import com.example.hotel_management.Model.UserDetails;
import com.example.hotel_management.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RequestOwnerController {
    private final UserDetailsServices userDetailsServices;
    private final RequestOwnerServices requestOwnerServices;
    private  final AuthoritiesServices authoritiesServices;

    @Autowired
    public RequestOwnerController(UserDetailsServices userDetailsServices,
                                RequestOwnerServices requestOwnerServices,
                                  AuthoritiesServices authoritiesServices) {
        this.userDetailsServices = userDetailsServices;
        this.requestOwnerServices = requestOwnerServices;
        this.authoritiesServices = authoritiesServices;
    }

    public List<UserDetails> getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userDetailsServices.findByUsername(username);
    }

    @GetMapping("/request-owner")
    public String requestOwner(Model model) {
        List<UserDetails> result = getUsername();
        UserDetails userDetails = null;
        if (result.isEmpty()) {
            return "redirect:/login";
        }
        else {
            userDetails = result.get(0);
        }
        model.addAttribute("userDetails", userDetails);

        RequestOwner request = new RequestOwner();
        request.setUsername(userDetails.getUsername());
        request.setIsAccepted(0);
        model.addAttribute("requestOwner", request);
        return "request_owner_form";
    }

    @PostMapping("/add-request-owner")
    public String addRequestOwner(@ModelAttribute("requestOwner") RequestOwner requestOwner, Model model) {
        List<RequestOwner> requestOwners = requestOwnerServices.findByUsername(requestOwner.getUsername());
        if (!requestOwners.isEmpty()) {
            requestOwnerServices.delete(requestOwners.get(0));
        }
        requestOwnerServices.save(requestOwner);
        return "redirect:/profile";
    }

    @GetMapping("owner-requests")
    public String ownerRequests(Model model) {
        List<RequestOwner> requestOwners = requestOwnerServices.findByIsAccepted(0);
        model.addAttribute("requestOwners", requestOwners);
        return "owner_request_page";
    }

    @GetMapping("owner-requests/accept")
    public String acceptOwner(@RequestParam("requestId") int requestId, Model model) {
        List<RequestOwner> result = requestOwnerServices.findByRequestId(requestId);
        if (result.isEmpty()) {
            return "redirect:/owner-requests";
        }

        // update isAccepted in request owner
        RequestOwner request = result.get(0);
        request.setIsAccepted(1);
        requestOwnerServices.save(request);

        // update user role

        String username = request.getUsername();
        Authorities newAuthority = new Authorities(username,"ROLE_OWNER");
        authoritiesServices.add(newAuthority);
        return "redirect:/owner-requests";
    }

    @GetMapping("owner-requests/reject")
    public String rejectOwner(@RequestParam("requestId") int requestId, Model model) {
        List<RequestOwner> result = requestOwnerServices.findByRequestId(requestId);
        if (result.isEmpty()) {
            return "redirect:/owner-requests";
        }

        // update isAccepted in request owner
        RequestOwner request = result.get(0);
        request.setIsAccepted(-1);
        requestOwnerServices.save(request);

        return "redirect:/owner-requests";
    }


}
