package com.artificial.intelligence.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class LlamaStreamingService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient webClient;

    public LlamaStreamingService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:11434") // Change to your LLaMA server URL
                .build();
    }

    public Flux<String> getStreamingData(List<String> dataStream) {

        //this process each prompts sequentially, once first prompt completed then goes to the second one
        return Flux.fromIterable(dataStream).concatMap(this::enrichSingleStream);

        // below code process both the prompts at a time and we get mixed response, that is first prompts result are merged with the second once
        // return Flux.fromIterable(dataStream).flatMap(this::enrichSingleStream);
    }

    public Flux<String> enrichSingleStream(String data) {
        return webClient.post()
                .uri("/api/generate")  // Change to your streaming endpoint
                .bodyValue(Map.of(
                        "model", "llama3",
                        "prompt", data,
                        "stream", true
                ))
                .retrieve()
                .bodyToFlux(String.class) // Receive response as a stream of strings (chunks)
                .map(this::extractTextChunk)
                .delayElements(Duration.ofMillis(50));
    }

    private String extractTextChunk(String chunk) {
        // For demo, just return chunk as-is
        try {
            JsonNode node = objectMapper.readTree(chunk);
            String response = node.has("response") ? node.get("response").asText() : "";
            System.out.println("Received :" + response);
            return response;
        } catch (Exception e) {
            // handle parsing errors, maybe log or return empty string
            System.err.println("Failed to parse chunk: " + chunk);
            return "";
        }
    }
}
