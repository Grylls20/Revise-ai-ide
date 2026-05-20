package com.reviseide.backend.strategy;

import com.reviseide.backend.dto.ExecutionRequest;
import com.reviseide.backend.dto.ExecutionResponse;
import com.reviseide.backend.factory.LanguageConfigFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PistonExecutionStrategy implements ExecutionStrategy {

    private final WebClient webClient;
    private final LanguageConfigFactory languageFactory;
    private static final String PISTON_URL = "https://emkc.org/api/v2/piston/execute";

    public PistonExecutionStrategy(LanguageConfigFactory languageFactory) {
        this.webClient = WebClient.create(PISTON_URL);
        this.languageFactory = languageFactory;
    }

    @Override
    public ExecutionResponse execute(ExecutionRequest request) {
        try {
            String languageName = languageFactory.getLanguageName(request.getLanguageId());
            
            // Build Piston Payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("language", languageName);
            payload.put("version", "*"); // Use whatever default version is available on the server
            
            List<Map<String, String>> files = new ArrayList<>();
            Map<String, String> file = new HashMap<>();
            file.put("content", request.getSourceCode());
            files.add(file);
            
            payload.put("files", files);
            
            if (request.getStdin() != null && !request.getStdin().isEmpty()) {
                payload.put("stdin", request.getStdin());
            }

            // Call Piston API
            Map<String, Object> response = webClient.post()
                    .header("Content-Type", "application/json")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null) {
                return ExecutionResponse.builder().status("Error").message("No response from Piston Server").build();
            }

            // Parse Piston Response
            Map<String, Object> compileResult = (Map<String, Object>) response.get("compile");
            Map<String, Object> runResult = (Map<String, Object>) response.get("run");
            
            String stdout = runResult != null ? (String) runResult.get("stdout") : "";
            String stderr = runResult != null ? (String) runResult.get("stderr") : "";
            String compileOutput = compileResult != null ? (String) compileResult.get("stderr") : "";
            
            // If compilation failed, the output is in compile result
            if (compileResult != null && compileResult.get("code") != null && ((Number) compileResult.get("code")).intValue() != 0) {
                return ExecutionResponse.builder()
                        .status("Compilation Error")
                        .compileOutput(compileOutput)
                        .stderr(compileOutput)
                        .stdout("")
                        .build();
            }

            return ExecutionResponse.builder()
                    .stdout(stdout)
                    .stderr(stderr)
                    .compileOutput(compileOutput)
                    .message(response.get("message") != null ? response.get("message").toString() : "")
                    .status("Success")
                    .time(0.0) // Piston doesn't return time directly in the basic structure
                    .memory(0)
                    .build();

        } catch (Exception e) {
            return ExecutionResponse.builder()
                    .status("Internal Server Error")
                    .message(e.getMessage())
                    .build();
        }
    }
}
