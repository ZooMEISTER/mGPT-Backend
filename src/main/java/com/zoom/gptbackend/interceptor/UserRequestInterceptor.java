package com.zoom.gptbackend.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.zoom.gptbackend.constant.PermissionLevel;
import com.zoom.gptbackend.exception.NoPermissionException;
import com.zoom.gptbackend.exception.UserNotExistException;
import com.zoom.gptbackend.exception.code.InterceptorResultCode;
import com.zoom.gptbackend.mapper.TouristMapper;
import com.zoom.gptbackend.pojo.po.UserPO;
import com.zoom.gptbackend.util.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.interceptor
 * @Project：gptbackend
 * @Name：UserRequestInterceptor
 * @Description: 用户请求拦截器
 * @Date：2025/4/27 10:07
 * @Filename：UserRequestInterceptor
 */

@Component
public class UserRequestInterceptor implements HandlerInterceptor {

    private TouristMapper touristMapper;
    public UserRequestInterceptor(TouristMapper touristMapper) {
        this.touristMapper = touristMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果请求是浏览器预检，直接放行
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // 获取请求路径
        String requestPath = request.getServletPath();
        // 期望的权限等级
        int expectPermissionLevel = -1;
        // 获取期望的权限等级
        if (requestPath.startsWith("/tourist")) { expectPermissionLevel = PermissionLevel.Tourist; }
        else if (requestPath.startsWith("/conversation")) { expectPermissionLevel = PermissionLevel.User; }
        else if (requestPath.startsWith("/chat")) { expectPermissionLevel = PermissionLevel.User; }
        else if (requestPath.startsWith("/blog")) { expectPermissionLevel = PermissionLevel.Tourist; }

        // 看看发送的请求是否需要鉴权
        if(expectPermissionLevel > PermissionLevel.Tourist && request.getHeader("Authorization") != null){
            try {
                // 获取请求头中的验证信息
                String token = request.getHeader("Authorization").substring(7);
                // 解析token
                Jws<Claims> claimsJws = JWTUtils.parseClaim(token);
                // 获取用户id
                String userid = String.valueOf(claimsJws.getPayload().get("userid"));
                // 去数据库中查询用户信息
                UserPO userPO = touristMapper.GetUserByUserid(userid);
                // 判断用户是否存在
                if(userPO == null) { throw  new UserNotExistException(); }
                // 判断用户的权限等级是否够
                if(userPO.getPermission() < expectPermissionLevel) { throw  new NoPermissionException(); }

                // 把用户的id塞到request中
                request.setAttribute("userId", userPO.getId());

                // 验证通过，放行
                return true;
            }
            catch (Exception e) {
                // 返回给前端的 JSON
                JSONObject jsonObject = new JSONObject();

                if(e instanceof SignatureException){
                    // token 无效
                    jsonObject.put("resultCode", InterceptorResultCode.INTERCEPTED_INVALID_TOKEN);
                    jsonObject.put("msg", "token 无效");
                }
                else if(e instanceof UserNotExistException){
                    // 用户不存在
                    jsonObject.put("resultCode", InterceptorResultCode.INTERCEPTED_USER_NOT_EXIST);
                    jsonObject.put("msg", "用户不存在");
                }
                else if(e instanceof NoPermissionException){
                    // 没有权限
                    jsonObject.put("resultCode", InterceptorResultCode.INTERCEPTED_NO_PERMISSION);
                    jsonObject.put("msg", "没有权限");
                }
                else if(e instanceof ExpiredJwtException){
                    // token过期
                    jsonObject.put("resultCode", InterceptorResultCode.INTERCEPTED_TOKEN_EXPIRED);
                    jsonObject.put("msg", "TOKEN 已过期");
                }

                String jsonObjectStr = JSONObject.toJSONString(jsonObject);
                ReturnJson(response, jsonObjectStr);

                return false;
            }
        }
        else if(expectPermissionLevel == PermissionLevel.Tourist){
            // 目前 tourist 开头的请求直接放行
            // 之后可能会做 ip 黑名单，防止某 ip 恶意访问
            return true;
        }
        else{
            // 非法请求，直接拦截
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode", InterceptorResultCode.INTERCEPTED_ILLEGAL_REQUEST);
            jsonObject.put("msg", "非法请求");
            String jsonObjectStr = JSONObject.toJSONString(jsonObject);
            ReturnJson(response, jsonObjectStr);

            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 拦截器的返回JSON数据方法
     * @DateTime: 2024/1/24 22:20
     * @Params:
     * @Return
     */
    private void ReturnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
