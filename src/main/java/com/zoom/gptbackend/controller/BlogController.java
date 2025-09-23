package com.zoom.gptbackend.controller;


import com.zoom.gptbackend.annotation.LogAnnotation;
import com.zoom.gptbackend.pojo.vo.result.DataResultVO;
import com.zoom.gptbackend.service.BlogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.controller
 * @Project：gptbackend
 * @Name：BlogController
 * @Description:
 * @Date：2025/9/16 16:49
 * @Filename：BlogController
 */

@RestController
@RequestMapping("/blog")
public class BlogController {
    private BlogService blogService;
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 用户获取所有博客接口
     * @DateTime: 2025/9/16 16:59
     * @Param: []
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @RequestMapping("/get-blogs")
    @LogAnnotation(description = "用户获取所有博客")
    public DataResultVO GetAllBlog() {
        return blogService.GetAllBlogs();
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 用户获取博客详情接口
     * @DateTime: 2025/9/17 10:18
     * @Param: [id]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @RequestMapping("/get-blog-detail")
    @LogAnnotation(description = "用户获取博客详情")
    public DataResultVO GetBlogDetail(@RequestParam("id") int id) {
        return blogService.GetBlogDetail(id);
    }
}
