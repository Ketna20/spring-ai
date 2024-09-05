package com.learn.spring_ai.controllers;

import com.learn.spring_ai.model.Answer;
import com.learn.spring_ai.model.GetCapitalRequest;
import com.learn.spring_ai.model.Question;
import com.learn.spring_ai.services.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {


    private final OpenAiService openAiService;


    public QuestionController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }


    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAiService.getAnswer(question);
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAiService.getCapital(getCapitalRequest);
    }
}
