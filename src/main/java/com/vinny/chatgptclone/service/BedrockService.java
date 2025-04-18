package com.vinny.chatgptclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;
import com.vinny.chatgptclone.config.AwsBedrockProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.regex.Pattern;

@Service
public class BedrockService {
    private final AwsBedrockProperties properties;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final int MAX_PROMPT_LENGTH = 10000;
    private static final Pattern PROMPT_PATTERN = Pattern.compile("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+$");

    @Autowired
    public BedrockService(AwsBedrockProperties properties) {
        this.properties = properties;
    }

    public String generateResponse(String prompt) {
        // Input validation
        if (!StringUtils.hasText(prompt)) {
            throw new IllegalArgumentException("Prompt cannot be empty");
        }

        if (prompt.length() > MAX_PROMPT_LENGTH) {
            throw new IllegalArgumentException("Prompt exceeds maximum length of " + MAX_PROMPT_LENGTH + " characters");
        }

        if (!PROMPT_PATTERN.matcher(prompt).matches()) {
            throw new IllegalArgumentException("Prompt contains invalid characters");
        }

        try (BedrockRuntimeClient bedrockClient = BedrockRuntimeClient.builder()
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {

            ObjectNode requestBody = objectMapper.createObjectNode();
            
            // Create messages array with sanitized input
            ArrayNode messages = requestBody.putArray("messages");
            ObjectNode message = messages.addObject();
            message.put("role", "user");
            message.put("content", sanitizeInput(prompt));

            // Add required parameters for Claude v2
            requestBody.put("max_tokens", Math.min(properties.getMaxTokens(), 4096)); // Limit max tokens
            requestBody.put("anthropic_version", "bedrock-2023-05-31");
            requestBody.put("temperature", Math.min(Math.max(properties.getTemperature(), 0.0), 1.0)); // Ensure temperature is between 0 and 1

            InvokeModelRequest request = InvokeModelRequest.builder()
                    .modelId(properties.getModelId())
                    .body(SdkBytes.fromUtf8String(objectMapper.writeValueAsString(requestBody)))
                    .build();

            InvokeModelResponse response = bedrockClient.invokeModel(request);
            String responseBody = response.body().asUtf8String();
            
            // Parse the JSON response and extract the content
            JsonNode responseJson = objectMapper.readTree(responseBody);
            String content = responseJson.get("content").get(0).get("text").asText();
            
            // Sanitize the response before returning
            return sanitizeOutput(content);
        } catch (Exception e) {
            // Log the error but don't expose internal details
            throw new RuntimeException("Error processing your request. Please try again later.");
        }
    }

    private String sanitizeInput(String input) {
        // Remove any potential HTML/JavaScript injection
        return input.replaceAll("<[^>]*>", "")
                   .replaceAll("javascript:", "")
                   .replaceAll("on\\w+=", "");
    }

    private String sanitizeOutput(String output) {
        // Basic output sanitization
        return output.replaceAll("<script.*?>.*?</script>", "")
                    .replaceAll("javascript:", "")
                    .replaceAll("on\\w+=", "");
    }
} 