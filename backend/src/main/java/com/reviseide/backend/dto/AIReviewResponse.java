package com.reviseide.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AIReviewResponse {
    private String reviewFeedback;
    private String suggestedRefactoring;
    private boolean bugsDetected;
}
