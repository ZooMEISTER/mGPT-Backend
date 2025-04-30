package com.zoom.gptbackend.pojo.vo.result;


import com.zoom.gptbackend.pojo.vo.ResultVO;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.pojo.vo.result
 * @Project：gptbackend
 * @Name：LoginResultVO
 * @Description:
 * @Date：2025/4/25 15:04
 * @Filename：LoginResultVO
 */

public class LoginResultVO extends ResultVO {
    private String userId;
    private String username;
    private String token;

    public LoginResultVO(int code, String msg, String userId, String username, String token) {
        super(code, msg);
        this.userId = userId;
        this.username = username;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResultVO{" +
                "userId='" + userId + '\'' +
                ", username=" + username +
                ", token='" + token + '\'' +
                '}';
    }
}
