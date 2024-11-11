package com.incture.OnlineQuizSystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// This class will handle exceptions globally across all controllers
@ControllerAdvice
public class GlobalExceptionHandler {
	// Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // Log the exception message if needed (optional)
        // log.error("An error occurred: {}", e.getMessage());

        // Return a response entity with the exception message and 500 status
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
