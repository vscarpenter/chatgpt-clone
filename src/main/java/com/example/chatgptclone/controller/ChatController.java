package com.example.chatgptclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.chatgptclone.service.BedrockService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ChatController {
    private final BedrockService bedrockService;

    @Autowired
    public ChatController(BedrockService bedrockService) {
        this.bedrockService = bedrockService;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletResponse response) {
        // Add security headers
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setHeader(HttpHeaders.EXPIRES, "0");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline';");
        
        model.addAttribute("response", "");
        return "index";
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String prompt, Model model, HttpServletResponse response) {
        // Add security headers
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setHeader(HttpHeaders.EXPIRES, "0");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline';");

        try {
            String responseText = bedrockService.generateResponse(prompt);
            model.addAttribute("response", responseText);
            model.addAttribute("prompt", prompt);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while processing your request. Please try again.");
            model.addAttribute("prompt", prompt);
        }
        return "index";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("error", "An unexpected error occurred. Please try again later.");
        return modelAndView;
    }
} 