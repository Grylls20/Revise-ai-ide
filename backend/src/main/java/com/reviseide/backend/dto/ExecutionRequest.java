package com.reviseide.backend.dto;

import lombok.Data;

@Data
public class ExecutionRequest {
    private String sourceCode;
    private int languageId;
    private String stdin;
}
