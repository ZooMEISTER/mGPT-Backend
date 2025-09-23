package com.zoom.gptbackend.util;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.util
 * @Project：gptbackend
 * @Name：OpenAiJsonFastjsonParser
 * @Description:
 * @Date：2025/4/27 16:52
 * @Filename：OpenAiJsonFastjsonParser
 */

public class OpenAiJsonFastjsonParser {
    /**
     * 这个是对话整体返回使用的函数
     * 使用Fastjson解析OpenAI返回的JSON字符串，提取choices下每个content（保留转义）
     * @param rawString OpenAI返回的原始字符串
     * @return List<String> 提取到的content列表
     */
    public static List<String> extractContentList(String rawString) {
        List<String> contentList = new ArrayList<>();

        try {
            // 1. 正则提前提取所有的 content 原始字符串
            List<String> originalContents = new ArrayList<>();
            Pattern pattern = Pattern.compile("\"content\"\\s*:\\s*\"(.*?)\"", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(rawString);
            while (matcher.find()) {
                originalContents.add(matcher.group(1));
            }

            // 2. 用 Fastjson 正常解析外层
            JSONObject root = JSON.parseObject(rawString);

            // 3. 遍历 choices，保持 content 的原样
            JSONArray choices = root.getJSONArray("choices");
            if (choices != null) {
                for (int i = 0; i < choices.size(); i++) {
                    JSONObject choice = choices.getJSONObject(i);
                    JSONObject message = choice.getJSONObject("message");
                    if (message != null && message.containsKey("content")) {
                        if (i < originalContents.size()) {
                            // 加入原始提取的 content
                            contentList.add(originalContents.get(i));
                        } else {
                            // 如果正则提取不到，就用解析后的（但通常不会发生）
                            contentList.add(message.getString("content"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("解析失败: " + e.getMessage());
        }

        return contentList;
    }

    /**
     * 这个是流式对话使用的函数
     * 从AI完整流式返回的原始内容中提取有用信息
     * @param fullRawContent OpenAI流式返回的累积文本
     * @return 提取后的干净文本
     */
    /**
     * 正确提取：不依赖 { } 配对，按块严格解析
     */
    public static String extractAiContent(String fullRawContent) {
        if (fullRawContent == null || fullRawContent.isBlank()) {
            return "";
        }

        // 1. 先去掉结尾的 [DONE]
        String content = fullRawContent.trim();
        if (content.endsWith("[DONE]")) {
            content = content.substring(0, content.lastIndexOf("[DONE]")).trim();
        }

        StringBuilder cleanText = new StringBuilder();
        int start = 0;
        int braceCount = 0;
        boolean insideString = false;

        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);

            if (ch == '"' && (i == 0 || content.charAt(i - 1) != '\\')) {
                insideString = !insideString;
            }

            if (!insideString) {
                if (ch == '{') {
                    if (braceCount == 0) {
                        start = i;
                    }
                    braceCount++;
                } else if (ch == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        // 截取完整的 JSON块
                        String jsonChunk = content.substring(start, i + 1);
                        parseAndAppendContent(jsonChunk, cleanText);
                    }
                }
            }
        }

        return cleanText.toString();
    }

    private static void parseAndAppendContent(String jsonString, StringBuilder cleanText) {
        try {
            JSONObject json = JSON.parseObject(jsonString);
            if (json.containsKey("choices")) {
                JSONObject choice = json.getJSONArray("choices").getJSONObject(0);
                if (choice.containsKey("delta")) {
                    JSONObject delta = choice.getJSONObject("delta");
                    if (delta != null && delta.containsKey("content") && delta.get("content") != null) {
                        cleanText.append(delta.getString("content"));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("解析失败，跳过一块内容: " + jsonString);
        }
    }
}
