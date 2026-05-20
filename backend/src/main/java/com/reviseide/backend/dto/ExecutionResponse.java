package com.reviseide.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecutionResponse {
    private String stdout;
    private String stderr;
    private String compileOutput;
    private String message;
    private String status;
    private double time;
    private int memory;
}
