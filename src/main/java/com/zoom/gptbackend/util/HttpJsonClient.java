package com.zoom.gptbackend.util;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.util
 * @Project：gptbackend
 * @Name：HttpJsonClient
 * @Description:
 * @Date：2025/4/27 15:29
 * @Filename：HttpJsonClient
 */

public class HttpJsonClient {

    private final RestTemplate restTemplate;

    public HttpJsonClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * 发送一个 POST 请求，Content-Type 固定为 application/json
     *
     * @param url  请求地址
     * @param body 请求体（Map格式）
     * @return 返回响应体字符串
     */
    public String postJson(String url, Map<String, Object> body) {
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 封装请求体
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return response.getBody();
        } catch (Exception e) {
            System.err.println("HTTP请求失败: " + e.getMessage());
            return e.getMessage();
        }
    }
}