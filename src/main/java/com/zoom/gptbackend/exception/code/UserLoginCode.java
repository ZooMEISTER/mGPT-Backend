package com.zoom.gptbackend.exception.code;


/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.exception.code
 * @Project：gptbackend
 * @Name：UserLoginCode
 * @Description:
 * @Date：2025/4/25 15:31
 * @Filename：UserLoginCode
 */

public interface UserLoginCode {
    public static final int USER_LOGIN_SUCCESS = 10000;
    public static final int USER_LOGIN_FAILED = 10001;
    public static final int USER_NOT_EXIST = 10002;
    public static final int WRONG_PASSWORD = 10003;
    public static final int TOKEN_EXPIRED = 10004;
    public static final int TOKEN_INVALID = 10005;
}
