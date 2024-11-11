package com.incture.OnlineQuizSystem.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Fields for registration (only username and password are required for login)
    private String username;
    private String password;
    
    // Additional fields for registration (not required for login)
    private String name;
    private String email;
    private String contact;
    
    // Default constructor
    public User() {}
    
    // Constructor for registration (with extra fields)
    public User(String username, String password, String name, String email, String contact) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contact = contact;
    }

    // Constructor for login (only username and password)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
