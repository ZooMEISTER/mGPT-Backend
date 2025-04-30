package com.zoom.gptbackend.pojo.bo;


import java.io.Serializable;
import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.pojo.bo
 * @Project：gptbackend
 * @Name：ChatRequest
 * @Description:
 * @Date：2025/4/28 09:20
 * @Filename：ChatRequest
 */

/**
 * ChatRequest类，用于接收前端发送过来的聊天请求。
 */
public class ChatRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    // 会话ID
    private String conversationId;

    // 选择的模型，比如 "gpt-4o"
    private String model;

    // 消息内容，注意是一个JSON数组格式的字符串
    private String rawJsonMessages;

    // ====== Getter & Setter ======

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRawJsonMessages() {
        return rawJsonMessages;
    }

    public void setRawJsonMessages(String rawJsonMessages) {
        this.rawJsonMessages = rawJsonMessages;
    }

    @Override
    public String toString() {
        return "ChatRequest{" +
                "conversationId='" + conversationId + '\'' +
                ", model='" + model + '\'' +
                ", rawJsonMessages='" + rawJsonMessages + '\'' +
                '}';
    }
}
