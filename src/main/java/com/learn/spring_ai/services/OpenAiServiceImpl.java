package com.learn.spring_ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private final ChatClient chatClient;

    public OpenAiServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatClient.CallPromptResponseSpec response = chatClient.prompt(prompt).call();

        // Step 3: Extract the answer from the response
        return response.content();
    }
}
