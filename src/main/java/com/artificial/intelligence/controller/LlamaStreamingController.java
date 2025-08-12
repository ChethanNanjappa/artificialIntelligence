package com.artificial.intelligence.controller;

import com.artificial.intelligence.service.LlamaStreamingService;
import jdk.jfr.StackTrace;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/stream")
public class LlamaStreamingController {

    private final LlamaStreamingService llamaStreamingService;

    public LlamaStreamingController(LlamaStreamingService llamaStreamingService) {
        this.llamaStreamingService = llamaStreamingService;
    }

    // get mapping cannot accept the request body as per spec
    @PostMapping("/getStream")
    public Flux<String> getStreamingData(@RequestBody List<String> dataStream) {
        return llamaStreamingService.getStreamingData(dataStream);
    }

   /*
   use below command to test the above end point
   curl -N -X POST http://localhost:8080/stream/getStream ^
     -H "Content-Type: application/json" ^
     -d "[\"Explain on Java\", \"Explain on React.js\"]"

            */

    /* use the below to check run in curl with username and password
    curl -i -u username:password -X POST http://localhost:8080/stream/getStream \
            -H "Content-Type: application/json" \
            -d '["Explain Java"]'
       */
}
