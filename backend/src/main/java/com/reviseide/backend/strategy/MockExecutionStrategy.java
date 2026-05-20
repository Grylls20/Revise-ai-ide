package com.reviseide.backend.strategy;

import com.reviseide.backend.dto.ExecutionRequest;
import com.reviseide.backend.dto.ExecutionResponse;
import org.springframework.stereotype.Component;

@Component("mockExecutionStrategy")
public class MockExecutionStrategy implements ExecutionStrategy {

    @Override
    public ExecutionResponse execute(ExecutionRequest request) {
        // Return a simulated response
        String simulatedOutput = "Simulated Execution Output for code:\n" +
                request.getSourceCode().substring(0, Math.min(request.getSourceCode().length(), 50)) +
                "...\n\n[Note: Real execution API required]";
                
        return ExecutionResponse.builder()
                .stdout(simulatedOutput)
                .stderr("")
                .compileOutput("")
                .message("Successfully mocked execution.")
                .status("Success")
                .time(0.01)
                .memory(1024)
                .build();
    }
}
