package com.mcd.langchain4jdemo.controller;

import com.mcd.langchain4jdemo.aiservice.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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

    @GetMapping(value = "/chat/stream", produces = "text/html;charset=utf-8")
    public Flux<String> chatStream(@RequestParam String memoryId, @RequestParam String message) {
        return chatService.chatFlux(memoryId, message);
    }

    @GetMapping("/translate")
    public String translate(@RequestParam String text, @RequestParam String targetLanguage) {
        return chatService.translate(text, targetLanguage);
    }
}