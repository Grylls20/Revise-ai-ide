package com.reviseide.backend.service;

import com.reviseide.backend.dto.AIReviewRequest;
import com.reviseide.backend.dto.AIReviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("geminiAIService")
public class GeminiAIService implements AIReviewService {

    private final WebClient webClient;
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent";
    
    @org.springframework.beans.factory.annotation.Value("${gemini.api.key}")
    private String apiKey;

    public GeminiAIService() {
        this.webClient = WebClient.create();
    }

    @Override
    public AIReviewResponse reviewCode(AIReviewRequest request) {
        try {
            // Build Gemini Payload
            Map<String, Object> payload = new HashMap<>();
            
            List<Map<String, Object>> contents = new ArrayList<>();
            Map<String, Object> content = new HashMap<>();
            List<Map<String, String>> parts = new ArrayList<>();
            Map<String, String> part = new HashMap<>();
            
            String promptText = "You are an expert AI pair programmer. Review the following " + request.getLanguage() + 
                                " code:\n\n```\n" + request.getSourceCode() + "\n```\n\n" +
                                "Provide short, concise feedback. Point out bugs if any, and always provide a short refactored snippet. Format your response cleanly.";
            part.put("text", promptText);
            parts.add(part);
            content.put("parts", parts);
            contents.add(content);
            payload.put("contents", contents);

            Map<String, Object> response = webClient.post()
                    .uri(java.net.URI.create(GEMINI_API_URL + "?key=" + apiKey))
                    .header("Content-Type", "application/json")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null || !response.containsKey("candidates")) {
                return fallbackResponse();
            }

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> resContent = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, String>> resParts = (List<Map<String, String>>) resContent.get("parts");
            String reviewText = resParts.get(0).get("text");

            return AIReviewResponse.builder()
                    .reviewFeedback(reviewText)
                    .suggestedRefactoring("See the code block in the review feedback above.")
                    .bugsDetected(reviewText.toLowerCase().contains("bug") || reviewText.toLowerCase().contains("error"))
                    .build();

        } catch (Exception e) {
            System.err.println("Gemini AI API Error: " + e.getMessage());
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
