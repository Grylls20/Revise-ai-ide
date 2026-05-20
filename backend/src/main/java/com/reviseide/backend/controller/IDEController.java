package com.reviseide.backend.controller;

import com.reviseide.backend.dto.AIReviewRequest;
import com.reviseide.backend.dto.AIReviewResponse;
import com.reviseide.backend.dto.ExecutionRequest;
import com.reviseide.backend.dto.ExecutionResponse;
import com.reviseide.backend.strategy.ExecutionStrategy;
import com.reviseide.backend.service.AIReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class IDEController {

    private final ExecutionStrategy executionStrategy;
    private final AIReviewService aiReviewService;

    @Autowired
    public IDEController(
            @org.springframework.beans.factory.annotation.Qualifier("wandboxExecutionStrategy") ExecutionStrategy executionStrategy,
            @org.springframework.beans.factory.annotation.Qualifier("geminiAIService") AIReviewService aiReviewService) {
        this.executionStrategy = executionStrategy;
        this.aiReviewService = aiReviewService;
    }

    @PostMapping("/execute")
    public ResponseEntity<ExecutionResponse> executeCode(@RequestBody ExecutionRequest request) {
        ExecutionResponse response = executionStrategy.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/review")
    public ResponseEntity<AIReviewResponse> reviewCode(@RequestBody AIReviewRequest request) {
        AIReviewResponse response = aiReviewService.reviewCode(request);
        return ResponseEntity.ok(response);
    }
}
