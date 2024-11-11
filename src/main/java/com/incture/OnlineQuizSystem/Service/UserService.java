package com.incture.OnlineQuizSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.OnlineQuizSystem.Entity.User;
import com.incture.OnlineQuizSystem.Repository.UserRepository;
import com.incture.UserException.AuthenticationFailedException;
import com.incture.UserException.UserAlreadyExistsException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Register method: Handle user registration (username, password, name, email, contact)
    public User register(User user) {
        // Check if the username already exists in the database
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists.");
        }

        // Save the new user
        return userRepository.save(user);
    }

    // Login method: Handle user login with username and password
    public void login(String username, String password) {
        // Find the user by username
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new AuthenticationFailedException("Invalid username or password."));
        
        // Check if the password matches
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationFailedException("Invalid username or password.");
        }
    }

    // Optionally, if you want to return the user details after successful login:
    public User getUserDetails(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new AuthenticationFailedException("User not found."));
    }
}
