package com.mcd.langchain4jdemo.aiservice;

import dev.langchain4j.service.SystemMessage;

public interface ChatService {

    @SystemMessage("你是一个友好的AI助手，请用中文回答问题。")
    String chat(String message);

    String translate(String text, String targetLanguage);
}