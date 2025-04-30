package com.zoom.gptbackend.controller;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zoom.gptbackend.pojo.bo.ChatRequest;
import com.zoom.gptbackend.pojo.vo.result.DataResultVO;
import com.zoom.gptbackend.service.ConversationService;
import com.zoom.gptbackend.util.JsonArrayHelper;
import com.zoom.gptbackend.util.OpenAiJsonFastjsonParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.controller
 * @Project：gptbackend
 * @Name：ChatController
 * @Description:
 * @Date：2025/4/28 09:18
 * @Filename：ChatController
 */

@RestController
@RequestMapping("/chat")
public class ChatController {

    public final ConversationService conversationService;
    public ChatController(ConversationService conversationService) { this.conversationService = conversationService; }

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:3002") // 你的Node代理地址
            .build();

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestBody ChatRequest chatRequest, HttpServletRequest request) {
        try {
            // 保存用户发送的信息
            conversationService.SaveUserMessages(
                    chatRequest.getConversationId(),
                    chatRequest.getModel(),
                    chatRequest.getRawJsonMessages()
            );

            // 解析 rawJsonMessages
            JSONArray messagesArray = JSON.parseArray(chatRequest.getRawJsonMessages());

            // 组装新的请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", chatRequest.getModel());
            requestBody.put("messages", messagesArray);
            requestBody.put("stream", true);

            // 保存 AI 的回复的对象
            AtomicReference<String> aiResponse = new AtomicReference<>("");

            // 更新对话的最后更新时间
            conversationService.UpdateConversationUpdateTime(chatRequest.getConversationId(), new Date());

            // 发请求 + 处理流式数据
            return webClient.post()
                    .uri("/api/chat")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse -> {
                        // 捕获代理服务器返回的非2xx错误
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    String errorMessage = "[代理服务器错误]: " + errorBody;
                                    System.err.println(errorMessage);
                                    return Mono.error(new RuntimeException(errorMessage));
                                });
                    })
                    .bodyToFlux(String.class)
                    .doOnNext(chunk -> {
                        // 累加 AI 返回的内容
                        aiResponse.updateAndGet(existing -> existing + chunk);
                    })
                    .doOnComplete(() -> {
                        // 流式完成后，保存完整的AI回复到数据库
                        String fullRawContent = aiResponse.get();
                        String cleanContent = OpenAiJsonFastjsonParser.extractAiContent(fullRawContent);
                        System.out.println(fullRawContent);
                        System.out.println(cleanContent);

                        conversationService.SaveAIMessages(
                                chatRequest.getConversationId(),
                                chatRequest.getModel(),
                                fullRawContent,
                                cleanContent
                        );
                    })
                    .onErrorResume(e -> {
                        // 捕获整个WebClient请求过程中的异常（包括代理服务器挂了、网络错误等）
                        String fallbackMessage;
                        if (e instanceof WebClientResponseException) {
                            fallbackMessage = "[WebClient错误]: " + ((WebClientResponseException) e).getResponseBodyAsString();
                        } else {
                            fallbackMessage = "[服务器内部错误]: " + e.getMessage();
                        }
                        System.err.println(fallbackMessage);

                        // 将错误作为Flux返回，推送给前端
                        return Flux.just(fallbackMessage);
                    });

        } catch (Exception e) {
            // 捕获本地后端处理中的错误，比如 JSON解析失败
            String errorMessage = "[后端解析异常]: " + e.getMessage();
            System.err.println(errorMessage);

            return Flux.just(errorMessage);
        }
    }
}
