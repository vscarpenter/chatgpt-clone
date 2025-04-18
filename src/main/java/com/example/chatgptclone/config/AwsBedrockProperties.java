package com.example.chatgptclone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "aws.bedrock")
public class AwsBedrockProperties {
    private String region;
    private String modelId;
    private Double temperature = 0.7;
    private Integer maxTokens = 1000;
} 