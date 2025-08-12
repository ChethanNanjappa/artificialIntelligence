package com.artificial.intelligence.service;

import com.artificial.intelligence.utility.Utilities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class ParallelizationWorkflowService {

    private final String systemPrompt = "You are a senior Java interviewer. " +
            "Generate 2 advanced interview questions for the subtopic mentioned, with brief explanation." +
            "and list them with bullet points separating the concepts and its respective questions";

    private final ChatClient chatClient;

    public ParallelizationWorkflowService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }


    public List<String> getParallelizationResult() {
        return parallel(Utilities.subtopicsForParallel, 5);
    }

    public List<String> parallel1(List<String> inputs, int parallelism) {
        return inputs.parallelStream()
                .map(input -> {
                    String fullPrompt = String.format("%s\n\n%s", systemPrompt, input);
                    return chatClient.prompt(fullPrompt).call().content();
                })
                .limit(parallelism)
                .collect(Collectors.toList());
    }


    public List<String> parallel(List<String> inputs, int parallelism) {
        ExecutorService executor = Executors.newFixedThreadPool(parallelism);

        try {
            List<CompletableFuture<String>> futures = inputs.stream()
                    .map(input -> CompletableFuture.supplyAsync(() -> {
                        String fullPrompt = String.format("%s\n\n%s", systemPrompt, input);
                        return chatClient.prompt(fullPrompt).call().content();
                    }, executor))
                    .collect(Collectors.toList());

            // Wait for all to complete and collect results
            return futures.stream()
                    .map(CompletableFuture::join) // join() throws unchecked exception if failed
                    .collect(Collectors.toList());
        } finally {
            executor.shutdown();
        }
    }
}
