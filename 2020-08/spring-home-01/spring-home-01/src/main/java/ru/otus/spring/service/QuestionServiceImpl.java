package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionDao;

import java.util.List;
import java.util.Map;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<String> getQuestionsToPrint() {
        return questionDao.getQuestions().stream().map(question -> {
            StringBuilder questionString = new StringBuilder();
            questionString.append(question.getQuestionIndex());
            questionString.append(") ");
            questionString.append(question.getText());
            for (Map.Entry<Integer, String> entry : question.getAnswers().entrySet()) {
                questionString.append('\n');
                questionString.append('\t');
                questionString.append(entry.getKey());
                questionString.append(". ");
                questionString.append(entry.getValue());
            }
            return questionString.toString();
        }).toList();
    }
}
