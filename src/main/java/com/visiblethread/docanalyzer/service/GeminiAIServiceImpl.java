package com.visiblethread.docanalyzer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class GeminiAIServiceImpl implements GeminiAIService {

    private final ChatClient chatClient;

    private static final Logger logger = LoggerFactory.getLogger(GeminiAIServiceImpl.class);

    public GeminiAIServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String generateTextResponse(String prompt) {
        logger.debug("Request to Gemini with the following prompt: {}", prompt);
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}