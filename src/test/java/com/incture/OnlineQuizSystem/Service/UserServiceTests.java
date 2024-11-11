package com.incture.OnlineQuizSystem.Service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.incture.OnlineQuizSystem.Entity.User;
import com.incture.OnlineQuizSystem.Repository.UserRepository;
import com.incture.UserException.AuthenticationFailedException;
import com.incture.UserException.UserAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;  // Mock the UserRepository

    @InjectMocks
    private UserService userService;  // Inject the mocked repository into the service

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testuser", "password123", "John Doe", "johndoe@example.com", "1234567890");
    }

    // Test for the register method when the user already exists
    @Test
    void testRegister_UserAlreadyExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.register(user);
        });

        assertEquals("User with username testuser already exists.", exception.getMessage());
    }

    // Test for the register method when the user does not exist
    @Test
    void testRegister_UserSuccess() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.register(user);

        assertNotNull(registeredUser);
        assertEquals("testuser", registeredUser.getUsername());
    }

    // Test for the login method with correct credentials
    @Test
    void testLogin_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.login(user.getUsername(), user.getPassword()));
    }

    // Test for the login method with incorrect username
    @Test
    void testLogin_InvalidUsername() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class, () -> {
            userService.login(user.getUsername(), user.getPassword());
        });

        assertEquals("Invalid username or password.", exception.getMessage());
    }

    // Test for the login method with incorrect password
    @Test
    void testLogin_InvalidPassword() {
        User wrongPasswordUser = new User("testuser", "wrongpassword", "John Doe", "johndoe@example.com", "1234567890");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class, () -> {
            userService.login(user.getUsername(), wrongPasswordUser.getPassword());
        });

        assertEquals("Invalid username or password.", exception.getMessage());
    }

    // Test for getting user details successfully
    @Test
    void testGetUserDetails_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserDetails(user.getUsername());

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    // Test for getting user details when user is not found
    @Test
    void testGetUserDetails_UserNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class, () -> {
            userService.getUserDetails(user.getUsername());
        });

        assertEquals("User not found.", exception.getMessage());
    }
}
