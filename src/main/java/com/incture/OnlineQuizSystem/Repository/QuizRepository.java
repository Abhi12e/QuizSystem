package com.incture.OnlineQuizSystem.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.OnlineQuizSystem.Entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    // Find quizzes by category
    List<Quiz> findByCategory(String category);

    // Find quizzes by difficulty level
    List<Quiz> findByDifficulty(String difficulty);

    // Find quizzes by both category and difficulty level
    List<Quiz> findByCategoryAndDifficulty(String category, String difficulty);

    // Find a limited number of quizzes by category and difficulty
    List<Quiz> findByCategoryAndDifficulty(String category, String difficulty, Pageable pageable);
}
