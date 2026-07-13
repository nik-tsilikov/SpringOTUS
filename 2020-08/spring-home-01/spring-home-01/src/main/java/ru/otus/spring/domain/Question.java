package ru.otus.spring.domain;

import java.util.Map;

public class Question {

    private final String text;
    private final int questionIndex;
    private final int correctAnswerIndex;
    private final Map<Integer, String> answers;

    public Question(String text, int questionIndex, int correctAnswerIndex, Map<Integer, String> answers) {
        this.text = text;
        this.questionIndex = questionIndex;
        this.correctAnswerIndex = correctAnswerIndex;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public Map<Integer, String> getAnswers() {
        return answers;
    }
}
