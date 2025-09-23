package com.zoom.gptbackend.service.impl;


import com.zoom.gptbackend.exception.code.DefaultResultCode;
import com.zoom.gptbackend.mapper.BlogMapper;
import com.zoom.gptbackend.pojo.po.BlogPO;
import com.zoom.gptbackend.pojo.vo.result.DataResultVO;
import com.zoom.gptbackend.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.service.impl
 * @Project：gptbackend
 * @Name：BlogServiceImpl
 * @Description:
 * @Date：2025/9/16 16:49
 * @Filename：BlogServiceImpl
 */

@Service
public class BlogServiceImpl implements BlogService {
    private BlogMapper blogMapper;
    public BlogServiceImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }


    /**
     * @Author: ZooMEISTER
     * @Description: 获取所有博客方法
     * @DateTime: 2025/9/16 16:58
     * @Param: []
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @Override
    public DataResultVO GetAllBlogs() {
        try{
            List<BlogPO> blogPOList = blogMapper.selectAllBlog();
            for (BlogPO blogPO : blogPOList) {
                String content = blogPO.getBlog_content();
                blogPO.setBlog_content(content.substring(0, Math.min(content.length(), 100)));
            }
            return new DataResultVO(DefaultResultCode.SUCCESS, String.valueOf(blogPOList.size()), blogPOList);
        } catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }

    /**
     * @Author: ZooMEISTER
     * @Description: 用户获取博客详情方法
     * @DateTime: 2025/9/17 10:18
     * @Param: [id]
     * @Return: com.zoom.gptbackend.pojo.vo.result.DataResultVO
     */
    @Override
    public DataResultVO GetBlogDetail(int id) {
        try{
            BlogPO blogPO = blogMapper.selectBlogById(id);
            if (blogPO == null) {
                return new DataResultVO(DefaultResultCode.ERROR, "未找到对应博客文章", null);
            }
            else{
                return new DataResultVO(DefaultResultCode.SUCCESS, String.valueOf(blogPO), blogPO);
            }
        } catch (Exception e) {
            return new DataResultVO(DefaultResultCode.ERROR, e.toString(), null);
        }
    }
}
