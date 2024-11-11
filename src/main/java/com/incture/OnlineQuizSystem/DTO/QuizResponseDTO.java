package com.incture.OnlineQuizSystem.DTO;

public class QuizResponseDTO {

    private int question_id;
    private String question_test;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;

    // Constructor
    public QuizResponseDTO(int question_id, String question_test, String option_a, String option_b, String option_c, String option_d) {
        this.question_id = question_id;
        this.question_test = question_test;
        this.option_a = option_a;
        this.option_b = option_b;
        this.option_c = option_c;
        this.option_d = option_d;
    }

    // Getters and Setters
    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_test() {
        return question_test;
    }

    public void setQuestion_test(String question_test) {
        this.question_test = question_test;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }
}
