package com.zoom.gptbackend.service.impl;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zoom.gptbackend.exception.code.DefaultResultCode;
import com.zoom.gptbackend.mapper.ConversationMapper;
import com.zoom.gptbackend.pojo.po.ChatMsgPO;
import com.zoom.gptbackend.pojo.po.ConversationPO;
import com.zoom.gptbackend.pojo.vo.result.DataResultVO;
import com.zoom.gptbackend.service.ConversationService;
import com.zoom.gptbackend.util.HttpJsonClient;
import com.zoom.gptbackend.util.JsonArrayHelper;
import com.zoom.gptbackend.util.OpenAiJsonFastjsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.service.impl
 * @Project：gptbackend
 * @Name：ConversationServiceImpl
 * @Description:
 * @Date：2025/4/25 16:57
 * @Filename：ConversationServiceImpl
 */

@Service
public class ConversationServiceImpl implements ConversationService {

    @Value("${gptproxy.url}")
    private String gptProxyUrl;

    ConversationMapper conversationMapper;
    public ConversationServiceImpl(ConversationMapper conversationMapper) { this.conversationMapper = conversationMapper; }

    /**
     * @Author: ZooMEISTER
     * @Description: 用户获取所有之前的对话的方法
     * @DateTime: 2025/4/27 09:51
     * @Param: [userId]
     * @Return: java.util.List<com.zoom.gptbackend.pojo.po.ConversationPO>
     */
    @Override
    public DataResultVO GetAllConversationsByUserId(String userId) {
        try{
            List<ConversationPO> conversationPOList = conversationMapper.GetAllConversationsByUserId(userId);
            return new DataResultVO(DefaultResultCode.SUCCESS, String.valueOf(conversationPOList.size()), conversationPOList);
        } catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 获取对话id下的历史内容
     * @DateTime: 2025/4/27 14:18
     * @Param: [conversationId]
     * @Return: java.util.List<com.zoom.gptbackend.pojo.po.ChatMsgPO>
     */
    @Override
    public DataResultVO GetConversationContentByConversationId(String conversationId) {
        try{
            List<ChatMsgPO> chatMsgPOList = conversationMapper.GetAllChatMsgsByConversationId(conversationId);
            return new DataResultVO(DefaultResultCode.SUCCESS, String.valueOf(chatMsgPOList.size()), chatMsgPOList);
        } catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 给 AI 发送消息方法 (已废弃)
     * @DateTime: 2025/4/27 15:33
     * @Param: [conversationId, model, content]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @Override
    public DataResultVO SendMessage(String conversationId, String model, String rawJsonMessages) {
        try{
            // 往数据库中写入用户发送的消息
            conversationMapper.InsertNewChatHistory(
                    UUID.randomUUID().toString(),
                    conversationId,
                    "user",
                    rawJsonMessages,
                    JsonArrayHelper.findLastUserRole(JSONArray.parseArray(rawJsonMessages)).getString("content"),
                    "USER",
                    new Date(),
                    0);
            // 组装要发送的对象
            Map<String, Object> body = new HashMap<>();
            body.put("model", model);
            body.put("messages", JSONArray.parseArray(rawJsonMessages));
            // 创建请求发送器
            HttpJsonClient client = new HttpJsonClient();
            // 发送请求获取回复
            String response = client.postJson(gptProxyUrl, body);
            // 把恢复转成JsonObject
            List<String> contentList = OpenAiJsonFastjsonParser.extractContentList(response);
            // 把 AI 的回复写入数据库
            for (String content : contentList) {
                conversationMapper.InsertNewChatHistory(
                        UUID.randomUUID().toString(),
                        conversationId,
                        "assistant",
                        response,
                        content,
                        model,
                        new Date(),
                        1
                );
            }

            return new DataResultVO(DefaultResultCode.SUCCESS, model, response);
        }
        catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }


    /**
     * @Author: ZooMEISTER
     * @Description: 创建新的对话方法
     * @DateTime: 2025/4/28 16:49
     * @Param: [userId, title]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @Override
    public DataResultVO CreateNewConversation(String userId, String title) {
        try{
            UUID newConversationId = UUID.randomUUID();
            int saveRes = conversationMapper.InsertNewConversation(newConversationId.toString(), userId, title);
            JSONObject returnJsonObject = new JSONObject();
            returnJsonObject.put("id", newConversationId.toString());
            returnJsonObject.put("title", title);
            return new DataResultVO(DefaultResultCode.SUCCESS, String.valueOf(saveRes), returnJsonObject);
        } catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 保存用户发给 AI 的对话数据
     * @DateTime: 2025/4/30 15:22
     * @Param: [conversationId, model, rawJsonMessages, createdTime]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @Override
    public DataResultVO SaveUserMessages(String conversationId, String model, String rawJsonMessages, Date createdTime) {
        try{
            // 往数据库中写入用户发送的消息
            int saveRes = conversationMapper.InsertNewChatHistory(
                    UUID.randomUUID().toString(),
                    conversationId,
                    "user",
                    rawJsonMessages,
                    JsonArrayHelper.findLastUserRole(JSONArray.parseArray(rawJsonMessages)).getString("content"),
                    "USER",
                    createdTime,
                    0
            );
            return new DataResultVO(DefaultResultCode.SUCCESS, String.valueOf(saveRes), null);
        } catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }


    /**
     * @Author: ZooMEISTER
     * @Description: 保存 AI 返回的生成信息
     * @DateTime: 2025/4/30 15:22
     * @Param: [conversationId, model, rawAIContent, cleanAIContent, createdTime]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @Override
    public DataResultVO SaveAIMessages(String conversationId, String model, String rawAIContent, String cleanAIContent, Date createdTime){
        try{
            int saveRes = conversationMapper.InsertNewChatHistory(
                    UUID.randomUUID().toString(),
                    conversationId,
                    "assistant",
                    rawAIContent,
                    cleanAIContent,
                    model,
                    createdTime,
                    1
            );
            return new DataResultVO(DefaultResultCode.SUCCESS, String.valueOf(saveRes), null);
        } catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 更新对话的最后更新时间
     * @DateTime: 2025/4/30 10:26
     * @Param: [conversationId, updateTime]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @Override
    public DataResultVO UpdateConversationUpdateTime(String conversationId, Date updateTime) {
        try{
            int res = conversationMapper.UpdateConversationUpdateTimeById(conversationId, updateTime);
            return new DataResultVO(DefaultResultCode.SUCCESS, String.valueOf(res), null);
        } catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 删除对话（逻辑删除）
     * @DateTime: 2025/4/30 10:26
     * @Param: [conversationId]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @Override
    public DataResultVO DeleteConversation(String conversationId) {
        try{
            int res = conversationMapper.DeleteConversationById(conversationId);
            return new DataResultVO(DefaultResultCode.SUCCESS, String.valueOf(res), null);
        } catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }
}
