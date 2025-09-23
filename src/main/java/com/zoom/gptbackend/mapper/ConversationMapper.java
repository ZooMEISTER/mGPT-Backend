package com.zoom.gptbackend.mapper;


import com.zoom.gptbackend.pojo.po.ChatMsgPO;
import com.zoom.gptbackend.pojo.po.ConversationPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.mapper
 * @Project：gptbackend
 * @Name：ConversationMapper
 * @Description: 对话相关的数据库操作
 * @Date：2025/4/25  16:56
 * @Filename：ConversationMapper
 */

@Mapper
public interface ConversationMapper {
    @Select("SELECT id,user_id,title,created_time,update_time FROM chat_conversation WHERE user_id=#{userId} AND is_deleted=0 ORDER BY update_time DESC")
    List<ConversationPO> GetAllConversationsByUserId(String userId);

    @Select("SELECT id,conversation_id,role,content,model,created_time FROM chat_history WHERE conversation_id=#{conversationId} ORDER BY created_time ASC, seq ASC")
    List<ChatMsgPO> GetAllChatMsgsByConversationId(String conversationId);

    @Insert("INSERT INTO chat_conversation (id,user_id,title) VALUES (#{id},#{user_id},#{title})")
    int InsertNewConversation(String id, String user_id, String title);

    @Insert("INSERT INTO chat_history (id,conversation_id,role,raw_content,content,model,created_time,seq) VALUES (#{id},#{conversation_id},#{role},#{raw_content},#{content},#{model},#{created_time},#{seq})")
    int InsertNewChatHistory(String id, String conversation_id, String role, String raw_content, String content, String model, Date created_time, int seq);

    @Update("UPDATE chat_conversation SET update_time=#{update_time} WHERE id=#{conversation_id}")
    int UpdateConversationUpdateTimeById(String conversation_id, Date update_time);

    @Update("UPDATE chat_conversation SET is_deleted=1 WHERE id=#{conversation_id}")
    int DeleteConversationById(String conversation_id);
}
