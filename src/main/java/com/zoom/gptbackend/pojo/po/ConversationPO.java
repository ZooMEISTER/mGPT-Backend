package com.zoom.gptbackend.pojo.po;


import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.pojo.po
 * @Project：gptbackend
 * @Name：ConversationPO
 * @Description: 聊天的对话对象
 * @Date：2025/4/27 09:40
 * @Filename：ConversationPO
 */

@TableName("chat_conversation")
public class ConversationPO {
    private String id;
    private String user_id;
    private String title;
    private Date create_time;
    private Date update_time;

    public ConversationPO(String id, String user_id, String title, Date create_time, Date update_time) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "ConversationPO{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
