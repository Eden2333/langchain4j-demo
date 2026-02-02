package com.mcd.langchain4jdemo.config;

import com.mcd.langchain4jdemo.aiservice.ChatService;
import com.mcd.langchain4jdemo.store.RedisChatMemoryStore;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LangChain4jConfig {

    private final RedisChatMemoryStore redisChatMemoryStore;

    @Value("${langchain4j.ollama.chat-model.base-url:http://localhost:11434}")
    private String baseUrl;

    @Value("${langchain4j.ollama.chat-model.model-name:llama2}")
    private String modelName;

    @Bean
    public OllamaChatModel ollamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    public OllamaStreamingChatModel streamingChatModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    public ChatService chatService() {
        return AiServices.builder(ChatService.class)
                .chatModel(ollamaChatModel())
                .streamingChatModel(streamingChatModel())
//                .chatMemory(chatMemory())
                .chatMemoryProvider(chatMemoryProvider())
                .build();
    }

//    @Bean
//    public ChatMemory chatMemory(){
//        // 实现会话记忆
//        return MessageWindowChatMemory.builder()
//                .maxMessages(20)
//                .build();
//    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .chatMemoryStore(redisChatMemoryStore)
                .build();
    }
}