package com.zoom.gptbackend.pojo.vo.result;


import com.zoom.gptbackend.pojo.vo.ResultVO;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.pojo.vo.result
 * @Project：gptbackend
 * @Name：DataResultVO
 * @Description:
 * @Date：2025/4/27 14:20
 * @Filename：DataResultVO
 */

public class DataResultVO extends ResultVO {
    private Object data;

    public DataResultVO(int code, String msg, Object data) {
        super(code, msg);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDataResultVO{" +
                "data=" + data +
                '}';
    }
}
