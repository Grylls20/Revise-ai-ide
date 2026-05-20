package com.reviseide.backend.service;

import com.reviseide.backend.dto.AIReviewRequest;
import com.reviseide.backend.dto.AIReviewResponse;

public interface AIReviewService {
    AIReviewResponse reviewCode(AIReviewRequest request);
}
