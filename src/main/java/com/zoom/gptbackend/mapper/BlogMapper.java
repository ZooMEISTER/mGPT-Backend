package com.zoom.gptbackend.mapper;


import com.zoom.gptbackend.pojo.po.BlogPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author：ZooMEISTER
 * @Package：com.zoom.gptbackend.mapper
 * @Project：gptbackend
 * @Name：BlogMapper
 * @Description:
 * @Date：2025/9/16 16:51
 * @Filename：BlogMapper
 */

@Mapper
public interface BlogMapper {
    @Select("SELECT id,blog_title,blog_content,created_time FROM sys_blog ORDER BY created_time DESC")
    List<BlogPO> selectAllBlog();

    @Select("SELECT id,blog_title,blog_content,created_time FROM sys_blog WHERE id=#{id}")
    BlogPO selectBlogById(int id);
}
