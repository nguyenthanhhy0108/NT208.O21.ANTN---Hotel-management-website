package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Config.SecurityConfig;
import com.example.hotel_management.Model.Users;
import com.example.hotel_management.Repository.AuthoritiesRepository;
import com.example.hotel_management.Repository.UserRepository;
import com.example.hotel_management.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
//Implement defined interface
@Service
public class UserServicesImpl implements UserServices {

    //Define some attribute
    private final UserRepository UserRepository;
    private final AuthoritiesRepository AuthoritiesRepository;
    private final PasswordEncoder encoder = SecurityConfig.passwordEncoder();
    //Dependency Injection
    @Autowired
    public UserServicesImpl(UserRepository userRepository,
                            AuthoritiesRepository authoritiesRepository) {
        UserRepository = userRepository;
        AuthoritiesRepository = authoritiesRepository;
    }
    //Call Repository layer and return  an Users list
    @Override
    public List<Users> findByUsername(String Username) {
        return UserRepository.findByUsername(Username);
    }

    //Call Repository layer and return  an users object
    @Override
    public Users save(Users users) {
        return UserRepository.save(users);
    }

    //Encode newPassword
    //Save it into database
    @Override
    public void updatePasswordByUsername(String username,
                                         String newPassword) {
        Users users = this.findByUsername(username).get(0);
        String encodedPassword = encoder.encode(newPassword);
        int enabled = users.getEnabled();

        if(enabled == 1){
            Users usersAfter = this.save(new Users(username, encodedPassword, enabled));
        }
    }

    //Encode rawPassword
    //Compare it with true password in database
    @Override
    public boolean comparePasswordByUsername(String username,
                                             String rawPassword) {
        Users users = this.findByUsername(username).get(0);
        String encodedPassword = encoder.encode(rawPassword);

        if(encodedPassword.equals(users.getPassword())){
            return true;
        }

        return false;
    }
    //Check user exist
    @Override
    public boolean checkUserExistByUsername(String Username) {
        List<Users> usersList = this.findByUsername(Username);
        if(usersList.isEmpty()) return false;
        else return true;
    }
}
