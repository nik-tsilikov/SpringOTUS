package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuestionDaoImplTest {

    @Test
    public void test1() {
        QuestionDaoImpl questionDao = new QuestionDaoImpl("/questions.csv");
        Assertions.assertEquals(6, questionDao.getQuestions().size());
    }
}
