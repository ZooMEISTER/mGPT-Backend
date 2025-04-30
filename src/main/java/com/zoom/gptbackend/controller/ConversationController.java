package com.zoom.gptbackend.controller;


import com.zoom.gptbackend.annotation.LogAnnotation;
import com.zoom.gptbackend.exception.code.DefaultResultCode;
import com.zoom.gptbackend.pojo.po.ChatMsgPO;
import com.zoom.gptbackend.pojo.po.ConversationPO;
import com.zoom.gptbackend.pojo.vo.result.DataResultVO;
import com.zoom.gptbackend.service.ConversationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.controller
 * @Project：gptbackend
 * @Name：ConversationController
 * @Description:
 * @Date：2025/4/25 16:59
 * @Filename：ConversationController
 */

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    public final ConversationService conversationService;
    public ConversationController(ConversationService conversationService) { this.conversationService = conversationService; }

    /**
     * @Author: ZooMEISTER
     * @Description: 用户获取之前的所有对话的接口
     * @DateTime: 2025/4/27 09:52
     * @Param: [userId]
     * @Return: java.util.List<com.zoom.gptbackend.pojo.po.ConversationPO>
     */
    @RequestMapping("/get-all-conversation")
    @LogAnnotation(description = "用户获取之前的所有对话")
    public DataResultVO GetAllConversationsByUserId(HttpServletRequest request) {
        return conversationService.GetAllConversationsByUserId(request.getAttribute("userId").toString());
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 用户创建新对话接口
     * @DateTime: 2025/4/28 16:49
     * @Param: [title, request]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @RequestMapping("/create-new-conversation")
    @LogAnnotation(description = "用户创建新对话")
    public DataResultVO CreateNewConversation(@RequestParam("title") String title, HttpServletRequest request){
        return conversationService.CreateNewConversation(request.getAttribute("userId").toString(), title);
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 获取指定对话的所有历史消息
     * @DateTime: 2025/4/27 14:28
     * @Param: [conversationId]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @RequestMapping("/get-conversation-content")
    @LogAnnotation(description = "用户获取指定对话的所有历史")
    public DataResultVO GetConversationContentByConversationId(@RequestParam("conversationId") String conversationId) {
        return conversationService.GetConversationContentByConversationId(conversationId);
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 用户给 AI 发送对话接口 (已废弃)
     * @DateTime: 2025/4/27 15:33
     * @Param: [conversationId, model, content]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @RequestMapping("/send-message")
    @LogAnnotation(description = "用户发送对话 (已废弃)")
    public DataResultVO SendMessage(@RequestParam("conversationId") String conversationId,
                                    @RequestParam("model") String model,
                                    @RequestParam("rawJsonMessages") String rawJsonMessages
    ) {
        return new DataResultVO(DefaultResultCode.ERROR, "此接口已废弃", null);
        // return conversationService.SendMessage(conversationId, model, rawJsonMessages);
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 删除对话
     * @DateTime: 2025/4/30 10:57
     * @Param: [conversationId]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @RequestMapping("/delete")
    @LogAnnotation(description = "用户删除对话")
    public DataResultVO Delete(@RequestParam("conversationId") String conversationId) {
        return conversationService.DeleteConversation(conversationId);
    }
}
