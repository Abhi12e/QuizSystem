package com.incture.OnlineQuizSystem.Service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.incture.OnlineQuizSystem.Entity.Quiz;
import com.incture.OnlineQuizSystem.Repository.QuizRepository;

@ExtendWith(MockitoExtension.class)
class QuizServiceTests{

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz(1, "What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "Paris", "Geography", "Easy");
    }

    // Test for fetching all quiz questions
    @Test
    void testGetAllQuestions() {
        when(quizRepository.findAll()).thenReturn(Arrays.asList(quiz));

        List<Quiz> quizzes = quizService.getAllQuestions();

        assertNotNull(quizzes);
        assertEquals(1, quizzes.size());
        assertEquals("What is the capital of France?", quizzes.get(0).getQuestion_test());
    }

    // Test for calculating score based on user-submitted answers
   /* @Test
    void testSubmitAnswers() {
        // Sample user answers (ensure these answers match the options in your data)
        List<String> userAnswers = Arrays.asList(
                "Paris",   // Q1: What is the capital of France? (Correct: Paris)
                "false",   // Q2: What is the default value of a boolean variable? (Correct: false)
                "Compilation", // Q3: Which of the following concepts is not a feature of OOP? (Correct: Compilation)
                "16 bits"  // Q4: What is the size of a `char` data type in Java? (Correct: 16 bits)
        );

        // Setup mock for quiz repository returning the quiz questions
        when(quizRepository.findAll()).thenReturn(Arrays.asList(
                new Quiz(1, "What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", "Paris", "Geography", "Medium"),
                new Quiz(2, "What is the default value of a boolean variable in Java?", "true", "false", "null", "0", "false", "Java", "Easy"),
                new Quiz(3, "Which of the following concepts is not a feature of Object-Oriented Programming (OOP)?", "Inheritance", "Encapsulation", "Polymorphism", "Compilation", "Compilation", "Java", "Medium"),
                new Quiz(4, "What is the size of a `char` data type in Java?", "8 bits", "16 bits", "32 bits", "64 bits", "16 bits", "Java", "Easy")
                // Add more questions as needed from your data
        ));

        // Call the service method to submit answers and calculate score
        String result = quizService.submitAnswers(userAnswers);

        // Check if the result contains correct answers and final score information
        assertNotNull(result);
        assertTrue(result.contains("Correct Answers"));
        assertTrue(result.contains("Final Score"));

        // Expected output: 4 questions, 4 correct answers, score should be 4
        String expectedResult = "Total Questions: 4\nCorrect Answers: 4\nWrong Answers: 0\nFinal Score: 4";
        assertEquals(expectedResult, result);
    }*/
    
    
    

    // Test for updating a quiz when not found
    @Test
    void testUpdateQuestion_QuizNotFound() {
        Quiz updatedQuiz = new Quiz(1, "Updated question?", "A", "B", "C", "D", "A", "General Knowledge", "Medium");

        when(quizRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            quizService.updateQuestion(1, updatedQuiz);
        });

        assertEquals("Quiz not found", exception.getMessage());
    }

    // Test for deleting a quiz
    @Test
    void testDeleteQuestion() {
        doNothing().when(quizRepository).deleteById(1);

        assertDoesNotThrow(() -> quizService.deleteQuestion(1));

        verify(quizRepository, times(1)).deleteById(1);
    }
    
    @Test
    void testSubmitAnswers() {
        // Sample user answers (ensure these answers match the options in your data)
        List<String> userAnswers = Arrays.asList(
                "Paris",   // Q1: What is the capital of France? (Correct: Paris)
                "false",   // Q2: What is the default value of a boolean variable? (Correct: false)
                "Compilation", // Q3: Which of the following concepts is not a feature of OOP? (Correct: Compilation)
                "16 bits"  // Q4: What is the size of a `char` data type in Java? (Correct: 16 bits)
        );

        // Setup mock for quiz repository returning the quiz questions
        when(quizRepository.findAll()).thenReturn(Arrays.asList(
                new Quiz(1, "What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", "Paris", "Geography", "Medium"),
                new Quiz(2, "What is the default value of a boolean variable in Java?", "true", "false", "null", "0", "false", "Java", "Easy"),
                new Quiz(3, "Which of the following concepts is not a feature of Object-Oriented Programming (OOP)?", "Inheritance", "Encapsulation", "Polymorphism", "Compilation", "Compilation", "Java", "Medium"),
                new Quiz(4, "What is the size of a `char` data type in Java?", "8 bits", "16 bits", "32 bits", "64 bits", "16 bits", "Java", "Easy")
                // Add more questions as needed from your data
        ));

        // Call the service method to submit answers and calculate score
        String result = quizService.submitAnswers(userAnswers);

        // Check if the result contains correct answers and final score information
        assertNotNull(result);
        assertTrue(result.contains("Final Score"));

        // The expected result should reflect 4 questions with 4 correct answers
        String expectedResult = "Final Score: 4";
        assertEquals(expectedResult, result);
    }

}
