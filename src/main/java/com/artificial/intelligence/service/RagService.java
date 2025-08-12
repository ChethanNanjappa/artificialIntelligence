package com.artificial.intelligence.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RagService {

    private final VectorStore vectorStore;

    private final ChatClient chatClient;

    public RagService(VectorStore vectorStore, ChatClient.Builder chatClient) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore)).build();
    }

    public String getPromptResult(String prompt) {
        return "Prompt Result : " + chatClient
                .prompt()
                .system("I need an exact result without any other information")
                .user(prompt)
                .call()
                .content();
    }
}
