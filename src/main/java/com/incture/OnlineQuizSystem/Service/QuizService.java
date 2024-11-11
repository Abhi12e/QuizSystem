package com.incture.OnlineQuizSystem.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.incture.OnlineQuizSystem.DTO.QuizResponseDTO;
import com.incture.OnlineQuizSystem.Entity.Quiz;

import com.incture.OnlineQuizSystem.Repository.QuizRepository;


@Service
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    @Autowired
    private QuizRepository quizRepository;

    // Fetch all quizzes
    public List<Quiz> getAllQuestions() {
        logger.info("Fetching all quiz questions.");
        return quizRepository.findAll();
    }

    // Fetch quizzes by category
    public List<Quiz> getQuestionsByCategory(String category) {
        logger.info("Fetching quiz questions by category: {}", category);
        return quizRepository.findByCategory(category);
    }

    // Fetch quizzes by difficulty
    public List<Quiz> getQuestionsByDifficulty(String difficulty) {
        logger.info("Fetching quiz questions by difficulty: {}", difficulty);
        return quizRepository.findByDifficulty(difficulty);
    }

    // Fetch quizzes by both category and difficulty
    public List<Quiz> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
        logger.info("Fetching quiz questions by category: {} and difficulty: {}", category, difficulty);
        return quizRepository.findByCategoryAndDifficulty(category, difficulty);
    }

    // Fetch a limited number of quizzes by category and difficulty
    public List<Quiz> getLimitedQuestionsByCategoryAndDifficulty(String category, String difficulty, int limit) {
        logger.info("Fetching a limited number of quiz questions by category: {}, difficulty: {}, limit: {}", category, difficulty, limit);
        return quizRepository.findByCategoryAndDifficulty(category, difficulty, PageRequest.of(0, limit));
    }

    // Add a new quiz to the database
    public Quiz addQuestion(Quiz quiz) {
        logger.info("Adding a new quiz question.");
        return quizRepository.save(quiz);
    }

    // Update an existing quiz
    public Quiz updateQuestion(int id, Quiz updatedQuiz) {
        logger.info("Updating quiz question with ID: {}", id);
        return quizRepository.findById(id).map(quiz -> {
            quiz.setQuestion_test(updatedQuiz.getQuestion_test());
            quiz.setOption_a(updatedQuiz.getOption_a());
            quiz.setOption_b(updatedQuiz.getOption_b());
            quiz.setOption_c(updatedQuiz.getOption_c());
            quiz.setOption_d(updatedQuiz.getOption_d());
            quiz.setCorrect_option(updatedQuiz.getCorrect_option());
            quiz.setCategory(updatedQuiz.getCategory());
            quiz.setDifficulty(updatedQuiz.getDifficulty());
            return quizRepository.save(quiz);
        }).orElseThrow(() -> {
            logger.error("Quiz with ID {} not found", id);
            return new RuntimeException("Quiz not found");
        });
    }

    // Delete a quiz by ID
    public void deleteQuestion(int id) {
        logger.info("Deleting quiz question with ID: {}", id);
        quizRepository.deleteById(id);
    }

    // Method to calculate score based on user-submitted answers
    /*public String submitAnswers(List<String> userAnswers) {
        int totalQuestions = userAnswers.size();
        int correctAnswers = 0;

        List<Quiz> questions = quizRepository.findAll();

        for (int i = 0; i < questions.size(); i++) {
            Quiz quiz = questions.get(i);
            String correctOption = quiz.getCorrect_option();
            String userAnswer = userAnswers.get(i);

            if (userAnswer != null && userAnswer.trim().equalsIgnoreCase(correctOption.trim())) {
                correctAnswers++;
            } else {
                logger.info("Incorrect answer for question ID {}: Expected {}, but got {}", quiz.getQuestion_id(), correctOption, userAnswer);
            }
        }

        int wrongAnswers = totalQuestions - correctAnswers;
        String resultMessage = String.format(
            "Total Questions: %d\nCorrect Answers: %d\nWrong Answers: %d\nFinal Score: %d",
            totalQuestions, correctAnswers, wrongAnswers, correctAnswers
        );

        logger.info("Quiz submitted. {}", resultMessage);
        return resultMessage;
    }*/
    
 // Submit answers and return score only
    public String submitAnswers(List<String> userAnswers) {
        int totalQuestions = userAnswers.size();
        int correctAnswers = 0;

        List<Quiz> questions = quizRepository.findAll();

        for (int i = 0; i < questions.size(); i++) {
            Quiz quiz = questions.get(i);
            String correctOption = quiz.getCorrect_option();
            String userAnswer = userAnswers.get(i);

            if (userAnswer != null && userAnswer.trim().equalsIgnoreCase(correctOption.trim())) {
                correctAnswers++;
            } else {
                logger.info("Incorrect answer for question ID {}: Expected {}, but got {}", quiz.getQuestion_id(), correctOption, userAnswer);
            }
        }

        int wrongAnswers = totalQuestions - correctAnswers;
        String resultMessage = String.format(
            "Final Score: %d", correctAnswers
        );

        logger.info("Quiz submitted. {}", resultMessage);
        return resultMessage;
    }

    
    // Fetch all questions without category, difficulty, and correct_option
    public List<QuizResponseDTO> getAllQuestionsWithoutCorrectOption() {
        return quizRepository.findAll()
                .stream()
                .map(quiz -> new QuizResponseDTO(
                        quiz.getQuestion_id(),
                        quiz.getQuestion_test(),
                        quiz.getOption_a(),
                        quiz.getOption_b(),
                        quiz.getOption_c(),
                        quiz.getOption_d()
                ))
                .collect(Collectors.toList());
    }
    
 // New method to return only questions and options (with DTO)
    public List<QuizResponseDTO> getQuestionsByCategoryWithOptionsOnly(String category) {
        logger.info("Fetching quiz questions by category with options only: {}", category);
        return quizRepository.findByCategory(category)
                .stream()
                .map(quiz -> new QuizResponseDTO(
                        quiz.getQuestion_id(),
                        quiz.getQuestion_test(),
                        quiz.getOption_a(),
                        quiz.getOption_b(),
                        quiz.getOption_c(),
                        quiz.getOption_d()
                ))
                .collect(Collectors.toList());
    }

    // Get detailed performance for a user based on submitted answers (user ID)
    public String getUserPerformance(int userId, List<String> userAnswers) {
        int totalQuestions = userAnswers.size();
        int correctAnswers = 0;

        List<Quiz> questions = quizRepository.findAll();

        for (int i = 0; i < questions.size(); i++) {
            Quiz quiz = questions.get(i);
            String correctOption = quiz.getCorrect_option();
            String userAnswer = userAnswers.get(i);

            if (userAnswer != null && userAnswer.trim().equalsIgnoreCase(correctOption.trim())) {
                correctAnswers++;
            } else {
                logger.info("Incorrect answer for question ID {}: Expected {}, but got {}", quiz.getQuestion_id(), correctOption, userAnswer);
            }
        }

        int wrongAnswers = totalQuestions - correctAnswers;

        String resultMessage = String.format(
            "User ID: %d\nTotal Questions: %d\nCorrect Answers: %d\nWrong Answers: %d\nFinal Score: %d",
            userId, totalQuestions, correctAnswers, wrongAnswers, correctAnswers
        );

        logger.info("Performance summary for User ID {}: {}", userId, resultMessage);
        return resultMessage;
    }


  

}
