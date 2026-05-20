package com.reviseide.backend.builder;

import java.util.HashMap;
import java.util.Map;

public class CodeSubmissionBuilder {
    private String sourceCode;
    private int languageId;
    private String stdin;

    public static CodeSubmissionBuilder builder() {
        return new CodeSubmissionBuilder();
    }

    public CodeSubmissionBuilder sourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
        return this;
    }

    public CodeSubmissionBuilder languageId(int languageId) {
        this.languageId = languageId;
        return this;
    }

    public CodeSubmissionBuilder stdin(String stdin) {
        this.stdin = stdin;
        return this;
    }

    public Map<String, Object> buildPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("source_code", this.sourceCode);
        payload.put("language_id", this.languageId);
        if (this.stdin != null && !this.stdin.isEmpty()) {
            payload.put("stdin", this.stdin);
        }
        return payload;
    }
}
