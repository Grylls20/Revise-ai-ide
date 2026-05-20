package com.reviseide.backend.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrokPromptBuilder {
    private String systemPrompt;
    private String userCode;
    private String language;

    public static GrokPromptBuilder builder() {
        return new GrokPromptBuilder();
    }

    public GrokPromptBuilder systemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
        return this;
    }

    public GrokPromptBuilder codeSnippet(String language, String userCode) {
        this.language = language;
        this.userCode = userCode;
        return this;
    }

    public Map<String, Object> buildPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "grok-beta");
        
        List<Map<String, String>> messages = new ArrayList<>();
        
        Map<String, String> systemMsg = new HashMap<>();
        systemMsg.put("role", "system");
        systemMsg.put("content", this.systemPrompt != null ? this.systemPrompt : "You are an expert code reviewer.");
        messages.add(systemMsg);
        
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", "Please review the following " + this.language + " code:\n\n```" + this.language + "\n" + this.userCode + "\n```\n\nProvide feedback on best practices, potential bugs, and a refactored version.");
        messages.add(userMsg);
        
        payload.put("messages", messages);
        payload.put("temperature", 0.3);
        
        return payload;
    }
}
