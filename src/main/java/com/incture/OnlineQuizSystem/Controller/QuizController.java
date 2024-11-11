package com.incture.OnlineQuizSystem.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incture.OnlineQuizSystem.DTO.QuizResponseDTO;
import com.incture.OnlineQuizSystem.Entity.Quiz;
import com.incture.OnlineQuizSystem.Service.QuizService;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuestions() {
        logger.info("Received request to fetch all quiz questions.");
        return ResponseEntity.ok(quizService.getAllQuestions());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Quiz>> getQuestionsByCategory(@PathVariable String category) {
        logger.info("Received request to fetch questions by category: {}", category);
        return ResponseEntity.ok(quizService.getQuestionsByCategory(category));
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<Quiz>> getQuestionsByDifficulty(@PathVariable String difficulty) {
        logger.info("Received request to fetch questions by difficulty: {}", difficulty);
        return ResponseEntity.ok(quizService.getQuestionsByDifficulty(difficulty));
    }

    @GetMapping("/category/{category}/difficulty/{difficulty}")
    public ResponseEntity<List<Quiz>> getQuestionsByCategoryAndDifficulty(
            @PathVariable String category, @PathVariable String difficulty) {
        logger.info("Received request to fetch questions by category: {} and difficulty: {}", category, difficulty);
        return ResponseEntity.ok(quizService.getQuestionsByCategoryAndDifficulty(category, difficulty));
    }

    @GetMapping("/category/{category}/difficulty/{difficulty}/limit/{limit}")
    public ResponseEntity<List<Quiz>> getLimitedQuestionsByCategoryAndDifficulty(
            @PathVariable String category, @PathVariable String difficulty, @PathVariable int limit) {
        logger.info("Received request to fetch limited questions by category: {}, difficulty: {}, limit: {}", category, difficulty, limit);
        return ResponseEntity.ok(quizService.getLimitedQuestionsByCategoryAndDifficulty(category, difficulty, limit));
    }

    @PostMapping
    public ResponseEntity<Quiz> addQuestion(@RequestBody Quiz quiz) {
        logger.info("Received request to add a new quiz question.");
        return ResponseEntity.status(HttpStatus.CREATED).body(quizService.addQuestion(quiz));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuestion(@PathVariable int id, @RequestBody Quiz updatedQuiz) {
        logger.info("Received request to update quiz question with ID: {}", id);
        try {
            return ResponseEntity.ok(quizService.updateQuestion(id, updatedQuiz));
        } catch (RuntimeException e) {
            logger.error("Error updating quiz question: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id) {
        logger.info("Received request to delete quiz question with ID: {}", id);
        quizService.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*@PostMapping("/submit")
    public ResponseEntity<String> submitAnswers(@RequestBody List<String> userAnswers) {
        logger.info("Received request to submit quiz answers.");
        String resultMessage = quizService.submitAnswers(userAnswers);
        return ResponseEntity.ok(resultMessage);
    }*/
    
    // Endpoint to submit quiz answers and get the score only
    @PostMapping("/submit")
    public ResponseEntity<String> submitQuizAnswers(@RequestBody List<String> userAnswers) {
        // Call the service to submit answers and get the score only
        String result = quizService.submitAnswers(userAnswers);
        
        // Return the score as a response
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/start")
    public ResponseEntity<List<QuizResponseDTO>> startQuiz() {
        List<QuizResponseDTO> quizQuestions = quizService.getAllQuestionsWithoutCorrectOption();
        return ResponseEntity.ok(quizQuestions);
    }
    
    @GetMapping("/start/category/{category}")
    public ResponseEntity<List<QuizResponseDTO>> getQuestionsByCategoryWithOptionsOnly(@PathVariable String category) {
        List<QuizResponseDTO> quizQuestions = quizService.getQuestionsByCategoryWithOptionsOnly(category);
        return ResponseEntity.ok(quizQuestions);
    }
    
    // Endpoint to get user performance summary based on user ID
    @PostMapping("/performance/{id}")
    public ResponseEntity<String> getUserPerformance(
            @PathVariable("id") int userId, 
            @RequestBody List<String> userAnswers) {
        
        // Call the service to get the detailed performance summary
        String performanceSummary = quizService.getUserPerformance(userId, userAnswers);
        
        // Return the performance summary as a response
        return ResponseEntity.ok(performanceSummary);
    }
    
 
   
}
