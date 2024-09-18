package com.Profpost.service;

import com.Profpost.model.entity.Blog;

import java.util.List;

public interface UserBlogService {
    List<Blog> findAll();
    Blog create(Blog blog);
    Blog findById(Integer id);
    Blog update(Integer id, Blog updateBlog);
    void delete(Integer id);
}
