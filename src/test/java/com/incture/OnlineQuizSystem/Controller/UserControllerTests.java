package com.incture.OnlineQuizSystem.Controller;

import com.incture.OnlineQuizSystem.Entity.User;
import com.incture.OnlineQuizSystem.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegisterUser() throws Exception {
        // Prepare the user data
        User newUser = new User("john_doe", "password123", "John Doe", "john.doe@example.com", "1234567890");
        User registeredUser = new User("john_doe", "password123", "John Doe", "john.doe@example.com", "1234567890");

        // Mock the register service method
        when(userService.register(any(User.class))).thenReturn(registeredUser);

        // Perform the POST request and verify the result
        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"john_doe\",\"password\":\"password123\",\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"contact\":\"1234567890\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.name").value("John Doe"))  // Use 'name' instead of 'firstName'
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.contact").value("1234567890"));

        // Verify that the register method was called once
        verify(userService, times(1)).register(any(User.class));
    }


    @Test
    public void testLoginUser() throws Exception {
        // Prepare the login credentials
        User loginRequest = new User("john_doe", "password123", null, null, null);

        // Mock the login service method (just verify that the method runs without errors)
        doNothing().when(userService).login(anyString(), anyString());

        // Perform the POST request and verify the result
        mockMvc.perform(post("/api/users/login")
                        .contentType("application/json")
                        .content("{\"username\":\"john_doe\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));

        // Verify that the login method was called once
        verify(userService, times(1)).login("john_doe", "password123");
    }
}
