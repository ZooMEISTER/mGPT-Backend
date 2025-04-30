package com.zoom.gptbackend.mapper;


import com.zoom.gptbackend.pojo.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.mapper
 * @Project：gptbackend
 * @Name：TouristMapper
 * @Description: 游客的操作
 * @Date：2025/4/25  14:01
 * @Filename：TouristMapper
 */

@Mapper
public interface TouristMapper {

    @Select("SELECT id,username,password,permission FROM sys_user WHERE id=#{userid}")
    public UserPO GetUserByUserid(String userid);

    @Select("SELECT id,username,password,permission FROM sys_user WHERE username=#{username}")
    public UserPO GetUserByUsername(String username);

}
