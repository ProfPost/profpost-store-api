package com.Profpost.service;

import com.Profpost.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();
    CategoryDTO findById(int id);
    CategoryDTO Create(CategoryDTO categoryDTO);
    CategoryDTO Update(Integer id, CategoryDTO updateCategoryDTO);
    void delete(Integer id);
}