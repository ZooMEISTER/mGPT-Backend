package com.zoom.gptbackend.service;


import com.zoom.gptbackend.pojo.po.UserPO;
import com.zoom.gptbackend.pojo.vo.result.LoginResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.service.impl
 * @Project：gptbackend
 * @Name：TouristService
 * @Description:
 * @Date：2025/4/25 14:02
 * @Filename：TouristService
 */

@Service
public interface TouristService {
    LoginResultVO Login(String username, String password);
    LoginResultVO AutoLogin(String authToken);
}