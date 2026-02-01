package com.mcd.langchain4jdemo.controller;

import com.mcd.langchain4jdemo.aiservice.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ollama")
public class AiController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        return chatService.chat(message);
    }

    @GetMapping("/chat")
    public String chatGet(@RequestParam String message) {
        return chatService.chat(message);
    }

//    @PostMapping("/translate")
//    public String translate(@RequestParam String text, @RequestParam String targetLanguage) {
//        return chatService.translate(text, targetLanguage);
//    }
}