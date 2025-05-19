package com.iss.learnloop.dto;

import com.iss.learnloop.model.User;

public class LoginResponse {
    private User user;
    private String userType;

    public LoginResponse(User user, String userType) {
        this.user = user;
        this.userType = userType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
