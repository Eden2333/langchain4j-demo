package com.mcd.langchain4jdemo.config;

import com.mcd.langchain4jdemo.aiservice.ChatService;
import com.mcd.langchain4jdemo.store.RedisChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content");
        // 创建向量数据库操作对象
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        // 创建 EmbeddingStoreIngestor 对象，通过调用 ingest 方法实现文本数据切割、向量化和存储
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(documents);
        return embeddingStore;
    }

    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> store) {
        /** 创建向量数据库检索对象
         minScore：最小匹配分数，分数越低，匹配度越低
         maxResults：最大返回结果数
         */
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .minScore(0.7)
                .maxResults(3)
                .build();
    }

    @Bean
    public ChatService chatService() {
        return AiServices.builder(ChatService.class)
                .chatModel(ollamaChatModel())
                .streamingChatModel(streamingChatModel())
//                .chatMemory(chatMemory())
                .chatMemoryProvider(chatMemoryProvider())
                .contentRetriever(contentRetriever(embeddingStore()))
                .build();
    }
}