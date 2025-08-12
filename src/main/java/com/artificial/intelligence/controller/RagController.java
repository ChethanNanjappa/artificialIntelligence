package com.artificial.intelligence.controller;

import com.artificial.intelligence.service.RagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rag")
public class RagController {

    @Autowired
    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/prompt")
    public String getPrompt(@RequestBody String prompt) {
        return ragService.getPromptResult(prompt);
    }
}
