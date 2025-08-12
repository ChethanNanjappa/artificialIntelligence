package com.artificial.intelligence.service;

import org.springframework.http.ResponseEntity;

public interface ModelTrainService {
    ResponseEntity<String> interactWithGptUsingAPiKey(String apiKey);

    ResponseEntity<String> interactWIthGptLLMModel(String prompt);
    String getAuthenticationToken(String userName);
}
