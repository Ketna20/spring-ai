package com.learn.spring_ai.controllers;

import com.learn.spring_ai.model.Answer;
import com.learn.spring_ai.model.Question;
import com.learn.spring_ai.services.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    @Autowired
    private OpenAiService openAiService;


    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAiService.getAnswer(question);
    }
}
