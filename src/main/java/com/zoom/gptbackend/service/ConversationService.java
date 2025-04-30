package com.zoom.gptbackend.service;


import com.zoom.gptbackend.pojo.po.ChatMsgPO;
import com.zoom.gptbackend.pojo.po.ConversationPO;
import com.zoom.gptbackend.pojo.vo.result.DataResultVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.service
 * @Project：gptbackend
 * @Name：ConversationService
 * @Description:
 * @Date：2025/4/25 16:57
 * @Filename：ConversationService
 */

@Service
public interface ConversationService {
    DataResultVO GetAllConversationsByUserId(String userId);
    DataResultVO GetConversationContentByConversationId(String conversationId);
    DataResultVO SendMessage(String conversationId, String model, String rawJsonMessages);
    DataResultVO CreateNewConversation(String userId, String title);
    DataResultVO SaveUserMessages(String conversationId, String model, String rawJsonMessages);
    DataResultVO SaveAIMessages(String conversationId, String model, String rawAIContent, String cleanAIContent);
    DataResultVO UpdateConversationUpdateTime(String conversationId, Date updateTime);
    DataResultVO DeleteConversation(String conversationId);
}
