package com.example.chatgptclone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "aws.bedrock")
public class AwsBedrockProperties {
    private String region = System.getenv("AWS_REGION");
    private String modelId = System.getenv("AWS_BEDROCK_MODEL_ID");
    private Double temperature = Double.parseDouble(System.getenv().getOrDefault("AWS_BEDROCK_TEMPERATURE", "0.7"));
    private Integer maxTokens = Integer.parseInt(System.getenv().getOrDefault("AWS_BEDROCK_MAX_TOKENS", "1000"));
} 