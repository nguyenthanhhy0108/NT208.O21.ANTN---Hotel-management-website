package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Users;

import java.util.List;
//Define services for Controller layer action easily
public interface UserServices {

    /**
     * This Service is used to find a list of Users object in database
     * @param Username: The username which you want to find Users object
     * @return
     * A List of Users object, can empty
     */
    List<Users> findByUsername(String Username);

    /**
     * This Service is used to check a user exist in database
     * @param Username: The username which you want to check
     * @return
     * A boolean.
     * True if username exist in database.
     * False for the remaining case
     */
    boolean checkUserExistByUsername(String Username);

    /**
     * This Service is used to save a user to database
     * @param Users: The Users object, which need to save
     * @return
     * A Users object, which has used in database
     */
    Users save(Users Users);

    /**
     * This Service is used to compare a raw string with true password in database
     * @param username: The username, which need to compare password
     * @param rawPassword: Raw password (For instance: abc12345@!)
     * @return
     * A boolean.
     * True if raw password match with password retrieved by provided username.
     * On the other hand this function will return false
     */
    boolean comparePasswordByUsername(String username,
                                      String rawPassword);

    /**
     * This Service is used to save a new password to database with the provided username
     * @param username: The username, which need to change password
     * @param newPassword: Raw password (For instance: abc12345@!)
     */
    void updatePasswordByUsername(String username,
                                  String newPassword);
}
