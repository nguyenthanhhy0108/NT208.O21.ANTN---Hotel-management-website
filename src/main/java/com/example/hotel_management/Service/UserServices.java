package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Users;

import java.util.List;
//Define services for Controller layer action easily
public interface UserServices {
    /*
    Input: String Username
    Output: List Users
     */
    List<Users> findByUsername(String Username);
    /*
    Input: String Username
    Output: Boolean
     */
    boolean checkUserExistByUsername(String Username);
    /*
    Input: Users object which you want to save
    Output: This object after saving process
     */
    Users save(Users Users);
    /*
    Input:
        - String username
        - String a raw password (For instance: abc12345@!)
    Output:
        - True if raw password match with password retrieved by provided username
        - On the other hand this function will return false
     */
    boolean comparePasswordByUsername(String username, String rawPassword);
    /*
    This function is used to save a new password to database by the provided username
    Input:
        - String username
        - String a new password (For instance: abc12345@!)
     */
    void updatePasswordByUsername(String username, String newPassword);
}
