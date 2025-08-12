package com.artificial.intelligence.controller;

import com.artificial.intelligence.service.PromptChainingService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chain")
public class PromptChainingController {

    private final PromptChainingService promptChainingService;

    public PromptChainingController(PromptChainingService promptChainingService) {
        this.promptChainingService = promptChainingService;
    }

    @GetMapping("/interviewHelper")
    public String interviewHelper(@RequestBody @RequestParam("interviewTopics") String topic) {
        return promptChainingService.processInputRequest(topic);
    }
}
