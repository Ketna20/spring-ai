package com.learn.spring_ai.services;

import com.learn.spring_ai.model.Answer;
import com.learn.spring_ai.model.GetCapitalRequest;
import com.learn.spring_ai.model.GetCapitalResponse;
import com.learn.spring_ai.model.Question;

public interface OpenAiService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getCapital(GetCapitalRequest getCapitalRequest);

    Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);

    Answer getCapitalInJson(GetCapitalRequest getCapitalRequest);

    GetCapitalResponse getCapitalResponseFormatted(GetCapitalRequest getCapitalRequest);
}
