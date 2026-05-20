package com.reviseide.backend.service;

import com.reviseide.backend.builder.GrokPromptBuilder;
import com.reviseide.backend.dto.AIReviewRequest;
import com.reviseide.backend.dto.AIReviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service("grokAIService")
public class GrokAIService implements AIReviewService {

    private final WebClient webClient;
    private static final String XAI_API_URL = "https://api.x.ai/v1/chat/completions";
    
    @org.springframework.beans.factory.annotation.Value("${xai.api.key}")
    private String apiKey;

    public GrokAIService() {
        this.webClient = WebClient.create(XAI_API_URL);
    }

    public AIReviewResponse reviewCode(AIReviewRequest request) {
        try {
            Map<String, Object> payload = GrokPromptBuilder.builder()
                    .systemPrompt("You are an expert AI pair programmer. Provide short, concise code reviews. Point out bugs if any, and always provide a short refactored snippet.")
                    .codeSnippet(request.getLanguage(), request.getSourceCode())
                    .buildPayload();

            Map<String, Object> response = webClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null || !response.containsKey("choices")) {
                return fallbackResponse();
            }

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String content = (String) message.get("content");

            return AIReviewResponse.builder()
                    .reviewFeedback(content)
                    .suggestedRefactoring("See the code block in the review feedback above.")
                    .bugsDetected(content.toLowerCase().contains("bug") || content.toLowerCase().contains("error"))
                    .build();

        } catch (Exception e) {
            System.err.println("Grok AI API Error: " + e.getMessage());
            return fallbackResponse();
        }
    }

    private AIReviewResponse fallbackResponse() {
        return AIReviewResponse.builder()
                .reviewFeedback("[MOCK MODE] Your code logic appears solid. Ensure you are handling edge cases.")
                .suggestedRefactoring("[MOCK MODE] Consider extracting this logic into a separate utility method.")
                .bugsDetected(false)
                .build();
    }
}
