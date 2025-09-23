package com.zoom.gptbackend.service;


import com.zoom.gptbackend.pojo.vo.result.DataResultVO;
import org.springframework.stereotype.Service;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.service
 * @Project：gptbackend
 * @Name：BlogService
 * @Description:
 * @Date：2025/9/16 16:49
 * @Filename：BlogService
 */

@Service
public interface BlogService {
    DataResultVO GetAllBlogs();
    DataResultVO GetBlogDetail(int id);
}
