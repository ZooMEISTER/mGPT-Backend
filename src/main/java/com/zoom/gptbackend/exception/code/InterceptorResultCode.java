package com.zoom.gptbackend.exception.code;


/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.exception.code
 * @Project：gptbackend
 * @Name：InterceptorResultCode
 * @Description:
 * @Date：2025/4/27 10:30
 * @Filename：InterceptorResultCode
 */

public interface InterceptorResultCode {
    public static final int INTERCEPTED_INVALID_TOKEN = 11001;
    public static final int INTERCEPTED_USER_NOT_EXIST = 11002;
    public static final int INTERCEPTED_NO_PERMISSION = 11003;
    public static final int INTERCEPTED_ILLEGAL_REQUEST = 11004;
    public static final int INTERCEPTED_TOKEN_EXPIRED = 11005;
}
