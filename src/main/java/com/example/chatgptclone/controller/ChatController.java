package com.example.chatgptclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.chatgptclone.service.BedrockService;

@Controller
public class ChatController {
    private final BedrockService bedrockService;

    @Autowired
    public ChatController(BedrockService bedrockService) {
        this.bedrockService = bedrockService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("response", "");
        return "index";
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String prompt, Model model) {
        String response = bedrockService.generateResponse(prompt);
        model.addAttribute("response", response);
        model.addAttribute("prompt", prompt);
        return "index";
    }
} 