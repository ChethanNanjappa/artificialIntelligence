package com.artificial.intelligence.service;

import com.artificial.intelligence.utility.Utilities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PromptChainingService {


    private final Map<String, String> systemPrompts = Utilities.promptChain;
    private final Map<String, String> programmingPrompts = Utilities.programPromptChain;
    private final ChatClient chatClient;
    private final RoutingWorkflowAgent routingWorkflowAgent;

    public PromptChainingService(ChatClient.Builder chatClient, RoutingWorkflowAgent routingWorkflowAgent) {
        this.chatClient = chatClient.build();
        this.routingWorkflowAgent = routingWorkflowAgent;
    }

    public String processInputRequest(String topic) {
        String routing = routingWorkflowAgent.route(topic);
        switch (routing) {
            case "conceptual" -> {
                System.out.println("Running conceptual question generator...");
                return getConceptual(topic);
            }
            case "programming" -> {
                System.out.println("Running programming task generator...");
                return getPrograms(topic);
            }
            default -> {
                System.out.println("Unknown type, falling back to general...");
                return getConceptual(topic); // fallback
            }
        }
    }

/*    Steps followed in the prompt chaining
            1) Assess the Topic
            2) Generate Latest Interview Questions
            3) Generate Expert-Level Answers
            4) Generate Simple Code Snippets
            5) Summarize & Polish Output*/

    public String getConceptual(String userInput) {
        String response = userInput;
        for (Map.Entry<String, String> entry : systemPrompts.entrySet()) {
            String stepName = entry.getKey();
            String promptTemplate = entry.getValue();
            String prompt = promptTemplate
                    .replace("{{topic}}", userInput)
                    .replace("{{input}}", response); // for later steps
            String input = String.format("%s\n\n%s", prompt, response);
            //System.out.println("Step: " + stepName + "\nInput to LLM:\n" + input + "\n");
            response = chatClient.prompt(input).call().content();
        }
        return response;
    }


    public String getPrograms(String userInput) {
        String response = userInput;
        for (Map.Entry<String, String> entry : programmingPrompts.entrySet()) {
            String stepName = entry.getKey();
            String promptTemplate = entry.getValue();
            String prompt = promptTemplate
                    .replace("{{topic}}", userInput)
                    .replace("{{input}}", response); // for later steps
            String input = String.format("%s\n\n%s", prompt, response);
            System.out.println("Step: " + stepName + "\nInput to LLM:\n" + input + "\n");
            response = chatClient.prompt(input).call().content();
        }
        return response;
    }
}
