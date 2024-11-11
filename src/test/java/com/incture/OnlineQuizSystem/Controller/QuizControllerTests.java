package com.incture.OnlineQuizSystem.Controller;

import com.incture.OnlineQuizSystem.Entity.Quiz;
import com.incture.OnlineQuizSystem.Service.QuizService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class QuizControllerTests {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        Quiz quiz = new Quiz(1, "What is Java?", "A programming language", "A type of coffee", "A country", "A machine", "A programming language", "Technology", "Easy");
        List<Quiz> quizList = Arrays.asList(quiz);

        when(quizService.getAllQuestions()).thenReturn(quizList);

        mockMvc.perform(get("/api/quizzes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question_test").value("What is Java?"))
                .andExpect(jsonPath("$[0].correct_option").value("A programming language"));
    }

    @Test
    public void testGetQuestionsByCategory() throws Exception {
        Quiz quiz = new Quiz(1, "What is Java?", "A programming language", "A type of coffee", "A country", "A machine", "A programming language", "Technology", "Easy");
        List<Quiz> quizList = Arrays.asList(quiz);

        when(quizService.getQuestionsByCategory("Technology")).thenReturn(quizList);

        mockMvc.perform(get("/api/quizzes/category/Technology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Technology"));
    }

    @Test
    public void testAddQuestion() throws Exception {
        Quiz quiz = new Quiz(1, "What is Java?", "A programming language", "A type of coffee", "A country", "A machine", "A programming language", "Technology", "Easy");

        when(quizService.addQuestion(any(Quiz.class))).thenReturn(quiz);

        mockMvc.perform(post("/api/quizzes")
                        .contentType("application/json")
                        .content("{\"question_test\":\"What is Java?\",\"option_a\":\"A programming language\",\"option_b\":\"A type of coffee\",\"option_c\":\"A country\",\"option_d\":\"A machine\",\"correct_option\":\"A programming language\",\"category\":\"Technology\",\"difficulty\":\"Easy\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.question_test").value("What is Java?"));
    }

    @Test
    public void testUpdateQuestion() throws Exception {
        Quiz existingQuiz = new Quiz(1, "What is Java?", "A programming language", "A type of coffee", "A country", "A machine", "A programming language", "Technology", "Easy");
        Quiz updatedQuiz = new Quiz(1, "What is Python?", "A programming language", "A type of coffee", "A country", "A machine", "A programming language", "Technology", "Medium");

        when(quizService.updateQuestion(eq(1), any(Quiz.class))).thenReturn(updatedQuiz);

        mockMvc.perform(put("/api/quizzes/{id}", 1)
                        .contentType("application/json")
                        .content("{\"question_test\":\"What is Python?\",\"option_a\":\"A programming language\",\"option_b\":\"A type of coffee\",\"option_c\":\"A country\",\"option_d\":\"A machine\",\"correct_option\":\"A programming language\",\"category\":\"Technology\",\"difficulty\":\"Medium\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question_test").value("What is Python?"));
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        doNothing().when(quizService).deleteQuestion(1);

        mockMvc.perform(delete("/api/quizzes/{id}", 1))
                .andExpect(status().isNoContent());

        verify(quizService, times(1)).deleteQuestion(1);
    }

    @Test
    public void testSubmitAnswers() throws Exception {
        String resultMessage = "You scored 5/10";

        when(quizService.submitAnswers(anyList())).thenReturn(resultMessage);

        mockMvc.perform(post("/api/quizzes/submit")
                        .contentType("application/json")
                        .content("[\"A programming language\", \"A type of coffee\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("You scored 5/10"));
    }
}
