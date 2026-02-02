package com.mcd.langchain4jdemo.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import reactor.core.publisher.Flux;

public interface ChatService {

     String SYSTEM_MESSAGE = "支援填报要考虑的因素有很多，如果要得到专业的支援填报指导，建议您预约一个一对一的指导服务，是否需要预约？";

    @SystemMessage("你是一个友好的AI助手，请用中文回答问题。")
    String chat(String message);

    @SystemMessage("你是一个友好的AI助手，请用中文回答问题。每次回答末尾加上一句话：<br/> " + SYSTEM_MESSAGE)
    // 只有一个参数时会默认处理为 UserMessage，有多个参数时需要使用注解指定各个属性
    Flux<String> chatFlux(@MemoryId String memoryId, @UserMessage String message);  // AiServices会根据返回值类型判断调用阻塞式还是流式模型

    @UserMessage("请将以下文本翻译为{{targetLanguage}}：{{text}}")
    String translate(@V("text") String text, @V("targetLanguage") String targetLanguage);
}