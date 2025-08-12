package com.artificial.intelligence.controller;

import com.artificial.intelligence.service.ModelTrainService;
import com.artificial.intelligence.service.ModelTrainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modelTrain")
public class ModelTrainController {


    @Value("${openAPi.key}")
    private String apiKey;

    @Autowired
    private ModelTrainService modelTrainService;

    @GetMapping("/getMessage")
    public ResponseEntity<String> modelTrainMethod() {
        return modelTrainService.interactWithGptUsingAPiKey(apiKey);
    }

    @GetMapping("/authenticate")
    public String getAuthenticationToken(@RequestBody String userName) {
        return modelTrainService.getAuthenticationToken(userName);
    }

}
