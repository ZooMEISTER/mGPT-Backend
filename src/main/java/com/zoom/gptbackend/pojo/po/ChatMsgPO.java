package com.zoom.gptbackend.pojo.po;


import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.pojo.po
 * @Project：gptbackend
 * @Name：ChatMsgPO
 * @Description: 用户的对话历史的每一句话
 * @Date：2025/4/27 09:45
 * @Filename：ChatMsgPO
 */

@TableName("chat_history")
public class ChatMsgPO {
    private String id;
    private String conversation_id;
    private String role;
    private String content;
    private String model;
    private Date create_time;

    public ChatMsgPO(String id, String conversation_id, String role, String content, String model, Date create_time) {
        this.id = id;
        this.conversation_id = conversation_id;
        this.role = role;
        this.content = content;
        this.model = model;
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "ChatMsgPO{" +
                "id='" + id + '\'' +
                ", conversation_id='" + conversation_id + '\'' +
                ", role='" + role + '\'' +
                ", content='" + content + '\'' +
                ", model='" + model + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
