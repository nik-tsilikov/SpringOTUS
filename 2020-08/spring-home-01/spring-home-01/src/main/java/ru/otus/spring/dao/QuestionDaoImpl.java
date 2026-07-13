package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QuestionDaoImpl implements QuestionDao{

    private final String sourcePath;
    private List<Question> questions;

    public QuestionDaoImpl(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Override
    public List<Question> getQuestions() {
        readQuestions();
        if (Objects.isNull(questions)) {
            throw new IllegalStateException("Questions read failed");
        }
        return questions;
    }

    private void readQuestions() {
        if (Objects.isNull(questions)) {
            List<Question> questionList = new ArrayList<>();
            try (InputStream inputStream = getClass().getResourceAsStream(sourcePath)) {
                if (Objects.isNull(inputStream)) {
                    throw new RuntimeException("Resource not found: " + sourcePath);
                }
                int lineIndex = 0;
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    String line;
                    boolean isHeader = true;
                    while (Objects.nonNull(line = reader.readLine())) {
                        if (isHeader) {
                            isHeader = false;
                            continue;
                        }
                        if (line.trim().isEmpty()) continue;

                        Question question = parseLine(lineIndex++, line);
                        questionList.add(question);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            questions = questionList;
        }
    }

    private Question parseLine(int lineIndex, String line) {
        List<String> tokens = parseCsvLine(line);

        if (tokens.size() < 4) {
            throw new IllegalArgumentException(String.format("Invalid question CSV line %s", line));
        }

        // Вопрос
        String questionText = tokens.getFirst();
        // Индекс правильного ответа
        int correctAnswerIndex = Integer.parseInt(tokens.get(1));
        // Пары индекс - текст варианта
        Map<Integer, String> answers = new HashMap<>();
        for (int i = 2; i < tokens.size(); i+= 2) {
            if (i + 1 < tokens.size()) {
                answers.put(Integer.parseInt(tokens.get(i)), tokens.get(i + 1));
            }
        }
        return new Question(questionText, lineIndex + 1, correctAnswerIndex, answers);
    }

    private List<String> parseCsvLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                insideQuotes = ! insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                tokens.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        tokens.add(current.toString().trim());

        return tokens;
    }
}
