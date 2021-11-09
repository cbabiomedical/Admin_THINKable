package com.example.adminthinkable;

public class User {

    private String userName, email;

    // Default constructor
    public User() {

    }

    // Parameterized constructor to create user object
    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;

    }

    // Getters and Setters to access variable outside class


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
