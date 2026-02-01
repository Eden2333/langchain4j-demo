package com.mcd.langchain4jdemo.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import reactor.core.publisher.Flux;

public interface ChatService {

    @SystemMessage("你是一个友好的AI助手，请用中文回答问题。")
    String chat(String message);

    @SystemMessage("你是一个友好的AI助手，请用中文回答问题。")
    // 只有一个参数时会默认处理为 UserMessage，有多个参数时需要使用注解指定各个属性
    Flux<String> chatFlux(@MemoryId String memoryId, @UserMessage String message);  // AiServices会根据返回值类型判断调用阻塞式还是流式模型

    @UserMessage("请将以下文本翻译为{{targetLanguage}}：{{text}}")
    String translate(@V("text") String text, @V("targetLanguage") String targetLanguage);
}