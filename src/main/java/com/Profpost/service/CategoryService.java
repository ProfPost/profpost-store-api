package com.Profpost.service;

import com.Profpost.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category findById(int id);
    Category Create(Category category);
    Category Update(Integer id, Category category);
    void delete(Integer id);
}
