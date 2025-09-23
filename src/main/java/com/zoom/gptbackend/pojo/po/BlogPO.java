package com.zoom.gptbackend.pojo.po;


import java.util.Date;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.pojo.po
 * @Project：gptbackend
 * @Name：BlogPO
 * @Description:
 * @Date：2025/9/16 16:52
 * @Filename：BlogPO
 */

public class BlogPO {
    private int id;
    private String blog_title;
    private String blog_content;
    private Date created_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    public String getBlog_content() {
        return blog_content;
    }

    public void setBlog_content(String blog_content) {
        this.blog_content = blog_content;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public BlogPO(int id, String blog_title, String blog_content, Date created_time) {
        this.id = id;
        this.blog_title = blog_title;
        this.blog_content = blog_content;
        this.created_time = created_time;
    }

    @Override
    public String toString() {
        return "BlogPO{" +
                "id=" + id +
                ", blog_title='" + blog_title + '\'' +
                ", blog_content='" + blog_content + '\'' +
                ", created_time=" + created_time +
                '}';
    }
}
