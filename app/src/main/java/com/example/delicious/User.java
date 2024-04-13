package com.example.delicious;

public class User {
    private String userID;
    private String email;
    // Add more fields as needed (e.g., username, profile picture URL, etc.)

    // Default constructor (required for Firebase)
    public User() {
    }

    // Constructor
    public User(String userID, String email) {
        this.userID = userID;
        this.email = email;
    }

    // Getters and setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

