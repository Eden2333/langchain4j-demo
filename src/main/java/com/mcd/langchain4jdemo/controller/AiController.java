package com.mcd.langchain4jdemo.controller;

import dev.langchain4j.model.chat.ChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ollama")
public class AiController {

    private final ChatModel model;

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        return model.chat(message);
    }

    @GetMapping("/chat")
    public String chatGet(@RequestParam String message) {
        return model.chat(message);
    }

//    @PostMapping("/translate")
//    public String translate(@RequestParam String text, @RequestParam String targetLanguage) {
//        return chatService.translate(text, targetLanguage);
//    }
}