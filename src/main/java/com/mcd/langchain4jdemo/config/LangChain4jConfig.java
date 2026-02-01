package com.mcd.langchain4jdemo.config;

import com.mcd.langchain4jdemo.aiservice.ChatService;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChain4jConfig {

    @Value("${langchain4j.ollama.chat-model.base-url:http://localhost:11434}")
    private String baseUrl;

    @Value("${langchain4j.ollama.chat-model.model-name:llama2}")
    private String modelName;

    @Bean
    public OllamaChatModel chatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    public ChatService chatService() {
        // 使用AiServices构建ChatService代理对象（本质上是一个model）
        return AiServices.builder(ChatService.class)
                .chatModel(chatLanguageModel())
                .build();
    }
}