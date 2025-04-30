package com.zoom.gptbackend.util;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.util
 * @Project：gptbackend
 * @Name：JsonArrayHelper
 * @Description:
 * @Date：2025/4/27 16:15
 * @Filename：JsonArrayHelper
 */

public class JsonArrayHelper {

    /**
     * 从JSONArray中，找到最后一个role为"user"的对象
     *
     * @param jsonArray 输入的JSONArray
     * @return 匹配到的JSONObject，如果没有则返回null
     */
    public static JSONObject findLastUserRole(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return null;
        }

        // 从后往前找
        for (int i = jsonArray.size() - 1; i >= 0; i--) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (obj != null && "user".equals(obj.getString("role"))) {
                return obj;
            }
        }
        // 没找到
        return null;
    }
}
