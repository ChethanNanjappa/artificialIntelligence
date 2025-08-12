package com.artificial.intelligence.controller;

import com.artificial.intelligence.service.ParallelizationWorkflowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parallel")
public class ParallelizationWorkflowController {

    private final ParallelizationWorkflowService parallelizationWorkflowService;

    public ParallelizationWorkflowController(ParallelizationWorkflowService parallelizationWorkflowService) {
        this.parallelizationWorkflowService = parallelizationWorkflowService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<String>> getResults() {
        List<String> response = parallelizationWorkflowService.getParallelizationResult();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
