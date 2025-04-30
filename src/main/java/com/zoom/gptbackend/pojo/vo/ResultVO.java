package com.zoom.gptbackend.pojo.vo;


/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.pojo.vo
 * @Project：gptbackend
 * @Name：ResultVO
 * @Description:
 * @Date：2025/4/25 15:02
 * @Filename：ResultVO
 */

public class ResultVO {
    private int code;
    private String msg;

    public ResultVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
