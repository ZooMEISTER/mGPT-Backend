package com.zoom.gptbackend.service.impl;


import com.zoom.gptbackend.exception.code.UserLoginCode;
import com.zoom.gptbackend.mapper.TouristMapper;
import com.zoom.gptbackend.pojo.po.UserPO;
import com.zoom.gptbackend.pojo.vo.result.LoginResultVO;
import com.zoom.gptbackend.service.TouristService;
import com.zoom.gptbackend.util.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.service.impl
 * @Project：gptbackend
 * @Name：TouristServiceImpl
 * @Description: 
 * @Date：2025/4/25  14:03
 * @Filename：TouristServiceImpl
 */

@Service
public class TouristServiceImpl implements TouristService {

    TouristMapper touristMapper;
    public TouristServiceImpl(TouristMapper touristMapper) {
        this.touristMapper = touristMapper;
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 用户登陆方法
     * @DateTime: 2025/4/25 15:13
     * @Param: [username, password]
     * @Return: com.zoom.gptbackend.pojo.vo.result.LoginResultVO
     */
    @Override
    public LoginResultVO Login(String username, String password) {
        UserPO userPO = touristMapper.GetUserByUsername(username);
        if(userPO == null){
            // 用户不存在
            return new LoginResultVO(
                UserLoginCode.USER_NOT_EXIST,
                "USER_NOT_EXIST",
                "null",
                "null",
                "null"
            );
        }
        if(userPO.getPassword().equals(password)){
            // 密码正确
            return new LoginResultVO(
                UserLoginCode.USER_LOGIN_SUCCESS,
                "USER_LOGIN_SUCCESS",
                userPO.getId(),
                userPO.getUsername(),
                JWTUtils.genAccessToken(userPO.getId())
            );
        }
        else{
            // 密码错误
            return new LoginResultVO(
                UserLoginCode.WRONG_PASSWORD,
                "WRONG_PASSWORD",
                "null",
                "null",
                "null"
            );
        }
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 自动登陆方法
     * @DateTime: 2025/4/25 15:57
     * @Param: [authToken]
     * @Return: com.zoom.gptbackend.pojo.vo.result.LoginResultVO
     */
    @Override
    public LoginResultVO AutoLogin(String authToken) {
        try{
            // 解析 Token
            Jws<Claims> tokenClaims = JWTUtils.parseClaim(authToken);
            // 获取用户信息
            UserPO userPO = touristMapper.GetUserByUserid(tokenClaims.getPayload().get("userid").toString());
            if(userPO == null){
                // 用户不存在
                return new LoginResultVO(
                        UserLoginCode.USER_NOT_EXIST,
                        "USER_NOT_EXIST",
                        "null",
                        "null",
                        "null"
                );
            }
            else{
                // 用户存在
                return new LoginResultVO(
                        UserLoginCode.USER_LOGIN_SUCCESS,
                        "USER_LOGIN_SUCCESS",
                        userPO.getId(),
                        userPO.getUsername(),
                        JWTUtils.genAccessToken(userPO.getId())
                );
            }
        }
        catch (Exception e){
            if(e instanceof ExpiredJwtException){
                // 令牌过期了
                return new LoginResultVO(
                        UserLoginCode.TOKEN_EXPIRED,
                        "TOKEN_EXPIRED",
                        "null",
                        "null",
                        "null"
                );
            }
            else if(e instanceof MalformedJwtException){
                // 令牌格式不对
                return new LoginResultVO(
                        UserLoginCode.TOKEN_INVALID,
                        "TOKEN_INVALID",
                        "null",
                        "null",
                        "null"
                );
            }
            else{
                return new LoginResultVO(
                        UserLoginCode.USER_LOGIN_FAILED,
                        e.toString(),
                        "null",
                        "null",
                        "null"
                );
            }
        }
    }
}
