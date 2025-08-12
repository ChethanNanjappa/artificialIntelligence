package com.artificial.intelligence.service;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpDecoderSpec;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ModelTrainServiceImpl implements ModelTrainService {


    private static final String SECRET = "q/els+Z0s4WBuI0u5oy7dUczt2SiCC2rKrsLm+DrUIS5TAS7H+PrKB0D4mY59Kxn55+wRVlI4nr2jEnt3OARDz8zi7jNa1+vZ8a4ikFD8GgreQyo+gkhXQZzggyjHakHTCSlJ+zZ6da/OQYKRdHh5Vb1qf8WFBvCU70+6MTBUQkyz8z5keRr7W7psGAA0YJ9f+Ut7+B7/orgMX/3l3AnmbH0gRk9Pjxctt37qb2cqtHDmHZpDGLBkbUy5wg7OF15nITg1mP52LVjsLxad/8nvxFzdac08e0Oghh2AdttJeKdKf4wwdjluxx2LAM6upKiW/3ZaqDKW5EerB5wSN37vPCBm+4/rykjIwKQu1jgH+U=";

    public ResponseEntity<String> interactWithGptUsingAPiKey(String apiKey) {
        String jsonRequest = """
                {
                  "model": "gpt-3.5-turbo",
                  "messages": [
                    {"role": "user", "content": "Tell me a fun fact about space."}
                  ]
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return new ResponseEntity<>("Error Processing Request", HttpStatus.OK);
        }
        System.out.println("Response:");
        System.out.println(response.body());
        return new ResponseEntity<>(response.body(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> interactWIthGptLLMModel(String prompt) {

        String jsonInput = "{ \"model\": \"llama3\", \"prompt\": \"" + prompt + "\", \"stream\": false }";


        WebClient client = WebClient.create("http://localhost:11434");
        String result = client.post()
                .uri("/api/generate")
                .bodyValue(jsonInput)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        return null;
    }

    @Override
    public String getAuthenticationToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        getJwtToken(claims, userName);
        return "";
    }

    private String getJwtToken(Map<String, Object> claims, String userName) {
        return null;
    }


}
