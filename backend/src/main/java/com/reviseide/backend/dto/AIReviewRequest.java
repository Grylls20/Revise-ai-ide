package com.reviseide.backend.dto;

import lombok.Data;

@Data
public class AIReviewRequest {
    private String sourceCode;
    private String language;
}
