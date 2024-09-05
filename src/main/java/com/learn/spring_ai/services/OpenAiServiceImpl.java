package com.learn.spring_ai.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.spring_ai.model.Answer;
import com.learn.spring_ai.model.GetCapitalRequest;
import com.learn.spring_ai.model.GetCapitalResponse;
import com.learn.spring_ai.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private final ChatClient chatClient;
    public OpenAiServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;


    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptWithInfo;

    @Value("classpath:templates/get-capital-info-json.st")
    private Resource getCapitalInfoJson;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatClient.CallPromptResponseSpec response = chatClient.prompt(prompt).call();

        // Step 3: Extract the answer from the response
        return response.content();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatClient.CallPromptResponseSpec response = chatClient.prompt(prompt).call();

        // Step 3: Extract the answer from the response
        return new Answer(response.content());
    }


    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatClient.CallPromptResponseSpec response = chatClient.prompt(prompt).call();

        // Step 3: Extract the answer from the response
        return new Answer(response.content());
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatClient.CallPromptResponseSpec response = chatClient.prompt(prompt).call();

        // Step 3: Extract the answer from the response
        return new Answer(response.content());
    }

    @Override
    public Answer getCapitalInJson(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatClient.CallPromptResponseSpec response = chatClient.prompt(prompt).call();

        String responseString;
        try {
            JsonNode jsonNode = objectMapper.readTree(response.content());
            responseString = jsonNode.get("answer").asText();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Answer(responseString);
    }

    @Override
    public GetCapitalResponse getCapitalResponseFormatted(GetCapitalRequest getCapitalRequest) {
        BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser(GetCapitalResponse.class);
        String format = parser.getFormat();
        System.out.println("Format: " + format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(), "format", format));
        ChatClient.CallPromptResponseSpec response = chatClient.prompt(prompt).call();

        // Step 3: Extract the answer from the response
        return parser.parse(response.content());
    }
}
