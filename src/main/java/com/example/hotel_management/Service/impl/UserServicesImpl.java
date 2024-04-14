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

    /**
     * Dependency Injection
     * @param userRepository: UserRepository object
     * @param authoritiesRepository: AuthoritiesRepository object
     */
    @Autowired
    public UserServicesImpl(UserRepository userRepository,
                            AuthoritiesRepository authoritiesRepository) {
        UserRepository = userRepository;
        AuthoritiesRepository = authoritiesRepository;
    }

    /**
     * Implement UserServices Interface
     * @param Username: The username which you want to find Users object
     * @return
     * A Users list
     */
    @Override
    public List<Users> findByUsername(String Username) {
        /*
        Call Repository layer and return a Users list
         */
        return UserRepository.findByUsername(Username);
    }

    /**
     * Implement UserServices Interface
     * @param users: The Users object, which need to save
     * @return
     * Users object
     */
    @Override
    public Users save(Users users) {
        /*
        Call Repository layer and return users object
         */
        return UserRepository.save(users);
    }

    /**
     * Implement UserServices Interface
     * @param username: The username, which need to change password
     * @param newPassword: Raw password (For instance: abc12345@!)
     */
    @Override
    public void updatePasswordByUsername(String username,
                                         String newPassword) {
        /*
        Encode newPassword
        Save it into database
         */
        Users users = this.findByUsername(username).get(0);
        String encodedPassword = encoder.encode(newPassword);
        int enabled = users.getEnabled();

        if(enabled == 1){
            Users usersAfter = this.save(new Users(username, encodedPassword, enabled));
        }
    }

    /**
     * Implement UserServices Interface
     * @param username: The username, which need to compare password
     * @param rawPassword: Raw password (For instance: abc12345@!)
     * @return
     * A boolean
     */
    @Override
    public boolean comparePasswordByUsername(String username,
                                             String rawPassword) {
        /*
        Encode rawPassword
        Compare it with true password in database
         */
        Users users = this.findByUsername(username).get(0);
        String encodedPassword = encoder.encode(rawPassword);

        if(encodedPassword.equals(users.getPassword())){
            return true;
        }

        return false;
    }

    /**
     * Implement UserServices Interface
     * @param Username: The username which you want to check
     * @return
     * A boolean
     */
    @Override
    public boolean checkUserExistByUsername(String Username) {
        /*
        Check user exist
         */
        List<Users> usersList = this.findByUsername(Username);
        if(usersList.isEmpty()) return false;
        else return true;
    }
}
