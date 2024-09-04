package com.learn.spring_ai.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAiServiceImplTest {
    @Autowired
    private OpenAiService openAiService;

    @Test
    void getAnswer() {
        String answer = openAiService.getAnswer("Write a python script to output numbers from 1 to 100.");
        System.out.println("Got the answer...");
        System.out.println(answer);
    }
}