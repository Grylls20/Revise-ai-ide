package com.reviseide.backend.strategy;

import com.reviseide.backend.dto.ExecutionRequest;
import com.reviseide.backend.dto.ExecutionResponse;
import com.reviseide.backend.builder.CodeSubmissionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class Judge0ExecutionStrategy implements ExecutionStrategy {

    private final WebClient webClient;
    // For production, use RapidAPI Judge0 endpoint or self-hosted endpoint
    private static final String JUDGE0_URL = "https://judge0-ce.p.rapidapi.com";
    
    @Value("${rapidapi.key}")
    private String apiKey;

    public Judge0ExecutionStrategy() {
        this.webClient = WebClient.create(JUDGE0_URL);
    }

    @Override
    public ExecutionResponse execute(ExecutionRequest request) {
        try {
            // Using Builder pattern to construct the payload Map
            Map<String, Object> payload = CodeSubmissionBuilder.builder()
                    .sourceCode(request.getSourceCode())
                    .languageId(request.getLanguageId())
                    .stdin(request.getStdin())
                    .buildPayload();

            // Wait=true to get synchronous response back
            Map<String, Object> response = webClient.post()
                    .uri("/submissions?base64_encoded=false&wait=true")
                    .header("X-RapidAPI-Key", apiKey)
                    .header("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null) {
                return ExecutionResponse.builder().status("Error").message("No response from Judge0").build();
            }

            return ExecutionResponse.builder()
                    .stdout((String) response.get("stdout"))
                    .stderr((String) response.get("stderr"))
                    .compileOutput((String) response.get("compile_output"))
                    .message((String) response.get("message"))
                    .status(parseStatus(response.get("status")))
                    .time(parseNumber(response.get("time")))
                    .memory(parseNumber(response.get("memory")).intValue())
                    .build();

        } catch (Exception e) {
            return ExecutionResponse.builder()
                    .status("Internal Server Error")
                    .message(e.getMessage())
                    .build();
        }
    }

    private String parseStatus(Object statusObj) {
        if (statusObj instanceof Map) {
            return (String) ((Map<?, ?>) statusObj).get("description");
        }
        return "Unknown";
    }

    private Double parseNumber(Object numberObj) {
        if (numberObj instanceof Number) {
            return ((Number) numberObj).doubleValue();
        } else if (numberObj instanceof String) {
            try {
                return Double.parseDouble((String) numberObj);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
}
