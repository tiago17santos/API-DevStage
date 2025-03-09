package com.techVerse.DevStage.Dtos;


import com.techVerse.DevStage.Entities.User;

public class UserDto {

    private int userID;
    private String userName;
    private String userEmail;

    public UserDto() {
    }

    public UserDto(String userName, String userEmail) {

        this.userName = userName;
        this.userEmail = userEmail;
    }

    public UserDto(User user) {
        userID = user.getUserId();
        userName = user.getUserName();
        userEmail = user.getUserEmail();
    }

    public int getUserId() {
        return userID;
    }

    public void setUserId(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
