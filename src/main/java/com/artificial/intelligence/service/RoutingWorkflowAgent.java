package com.artificial.intelligence.service;

import com.artificial.intelligence.utility.Utilities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RoutingWorkflowAgent {

    private final ChatClient chatClient;

    public RoutingWorkflowAgent(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public String route(String userInput) {
        Map<String, String> routes = Utilities.routes;
        // Step 1: Ask LLM to classify the intent
        String classificationPrompt = String.format("""
                You are an AI router. Analyze the following request and classify it into one of these categories:
                %s
                
                User input:
                "%s"
                
                Respond only with one of the category keys.
                """, String.join(", ", routes.keySet()), userInput);

        // Step 2: Call LLM to classify
        String classification = chatClient.prompt(classificationPrompt)
                .call()
                .content()
                .trim()
                .toLowerCase()
                .replaceAll("[^a-z]", "");

        // Step 3: Use routed prompt
        return classification;
    }
}
