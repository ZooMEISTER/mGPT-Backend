package com.zoom.gptbackend.controller;


import com.zoom.gptbackend.annotation.LogAnnotation;
import com.zoom.gptbackend.pojo.po.UserPO;
import com.zoom.gptbackend.pojo.vo.result.LoginResultVO;
import com.zoom.gptbackend.service.TouristService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.controller
 * @Project：gptbackend
 * @Name：TouristController
 * @Description: 
 * @Date：2025/4/25  14:04
 * @Filename：TouristController
 */

@RestController
@RequestMapping("/tourist")
public class TouristController {

    private final TouristService touristService;
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("/test")
    @LogAnnotation(description = "游客 Controller 测试接口")
    public String TouristTest(){
        return "Tourist Controller Test !!";
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 用户登录接口
     * @DateTime: 2025/4/25 15:13
     * @Param: [username, password]
     * @Return: com.zoom.gptbackend.pojo.vo.result.LoginResultVO
     */
    @PostMapping("/login")
    @LogAnnotation(description = "游客登陆")
    public LoginResultVO TouristLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        return touristService.Login(username, password);
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 自动登录接口
     * @DateTime: 2025/4/25 15:58
     * @Param: [authToken]
     * @Return: com.zoom.gptbackend.pojo.vo.result.LoginResultVO
     */
    @PostMapping("/autologin")
    @LogAnnotation(description = "自动登录接口")
    public LoginResultVO TouristAutoLogin(@RequestParam("authToken") String authToken){
        return touristService.AutoLogin(authToken);
    }
}
