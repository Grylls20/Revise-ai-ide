package com.reviseide.backend.strategy;

import com.reviseide.backend.dto.ExecutionRequest;
import com.reviseide.backend.dto.ExecutionResponse;
import com.reviseide.backend.factory.LanguageConfigFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component("wandboxExecutionStrategy")
public class WandboxExecutionStrategy implements ExecutionStrategy {

    private final WebClient webClient;
    private final LanguageConfigFactory languageFactory;
    private static final String WANDBOX_URL = "https://wandbox.org/api/compile.json";

    public WandboxExecutionStrategy(LanguageConfigFactory languageFactory) {
        this.webClient = WebClient.create(WANDBOX_URL);
        this.languageFactory = languageFactory;
    }

    @Override
    public ExecutionResponse execute(ExecutionRequest request) {
        try {
            String languageName = languageFactory.getLanguageName(request.getLanguageId());
            
            if ("java".equals(languageName)) {
                return ExecutionResponse.builder()
                        .stdout("[Mock Output] Java is not natively supported on this free Wandbox instance.\nHello, Revise AI!")
                        .stderr("")
                        .compileOutput("")
                        .message("Mock Execution")
                        .status("Success")
                        .time(0.0)
                        .memory(0)
                        .build();
            }

            String compiler = getWandboxCompiler(languageName);
            
            Map<String, Object> payload = new HashMap<>();
            payload.put("compiler", compiler);
            payload.put("code", request.getSourceCode());
            
            if (request.getStdin() != null && !request.getStdin().isEmpty()) {
                payload.put("stdin", request.getStdin());
            }

            String responseStr = webClient.post()
                    .header("Content-Type", "application/json")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Map<String, Object> response = null;
            if (responseStr != null) {
                org.springframework.boot.json.JsonParser parser = org.springframework.boot.json.JsonParserFactory.getJsonParser();
                response = parser.parseMap(responseStr);
            }

            if (response == null) {
                return ExecutionResponse.builder().status("Error").message("No response from Wandbox").build();
            }

            String programMessage = (String) response.getOrDefault("program_message", "");
            String programError = (String) response.getOrDefault("program_error", "");
            String compilerMessage = (String) response.getOrDefault("compiler_message", "");
            String compilerError = (String) response.getOrDefault("compiler_error", "");
            String statusObj = response.get("status") != null ? response.get("status").toString() : "1";
            
            String combinedStdout = programMessage != null ? programMessage : "";
            String combinedStderr = programError != null ? programError : "";
            String combinedCompile = (compilerMessage != null ? compilerMessage : "") + "\n" + (compilerError != null ? compilerError : "");

            return ExecutionResponse.builder()
                    .stdout(combinedStdout)
                    .stderr(combinedStderr)
                    .compileOutput(combinedCompile.trim())
                    .message("Execution completed.")
                    .status(statusObj.equals("0") ? "Success" : "Error")
                    .time(0.0)
                    .memory(0)
                    .build();

        } catch (Exception e) {
            return ExecutionResponse.builder()
                    .status("Internal Server Error")
                    .message(e.getMessage())
                    .build();
        }
    }

    private String getWandboxCompiler(String lang) {
        switch (lang) {
            case "python": return "cpython-3.12.7";
            case "java": return "openjdk-head"; // Java not supported on Wandbox natively, might fail
            case "cpp": return "gcc-12.3.0";
            case "javascript": return "nodejs-20.17.0";
            case "go": return "go-1.23.2";
            case "rust": return "rust-1.82.0";
            case "ruby": return "ruby-3.4.9";
            default: return "gcc-12.3.0";
        }
    }
}
