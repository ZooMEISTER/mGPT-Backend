package com.zoom.gptbackend.pojo.po;


import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.pojo.po
 * @Project：gptbackend
 * @Name：UserPO
 * @Description: 用户类
 * @Date：2025/4/25  14:01
 * @Filename：UserPO
 */

@TableName("sys_user")
public class UserPO {

    private String id;
    private String username;
    private String password;
    private int permission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "UserPO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                '}';
    }
}
