package com.example.chatgptclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;
import com.example.chatgptclone.config.AwsBedrockProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class BedrockService {
    private final AwsBedrockProperties properties;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public BedrockService(AwsBedrockProperties properties) {
        this.properties = properties;
    }

    public String generateResponse(String prompt) {
        try (BedrockRuntimeClient bedrockClient = BedrockRuntimeClient.builder()
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {

            ObjectNode requestBody = objectMapper.createObjectNode();
            
            // Create messages array
            ArrayNode messages = requestBody.putArray("messages");
            ObjectNode message = messages.addObject();
            message.put("role", "user");
            message.put("content", prompt);

            // Add required parameters for Claude v2
            requestBody.put("max_tokens", properties.getMaxTokens());
            requestBody.put("anthropic_version", "bedrock-2023-05-31");
            requestBody.put("temperature", properties.getTemperature());

            InvokeModelRequest request = InvokeModelRequest.builder()
                    .modelId(properties.getModelId())
                    .body(SdkBytes.fromUtf8String(objectMapper.writeValueAsString(requestBody)))
                    .build();

            InvokeModelResponse response = bedrockClient.invokeModel(request);
            String responseBody = response.body().asUtf8String();
            
            // Parse the JSON response and extract the content
            JsonNode responseJson = objectMapper.readTree(responseBody);
            return responseJson.get("content").get(0).get("text").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error calling Bedrock API", e);
        }
    }
} 