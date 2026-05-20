package com.reviseide.backend.strategy;

import com.reviseide.backend.dto.ExecutionRequest;
import com.reviseide.backend.dto.ExecutionResponse;

public interface ExecutionStrategy {
    ExecutionResponse execute(ExecutionRequest request);
}
