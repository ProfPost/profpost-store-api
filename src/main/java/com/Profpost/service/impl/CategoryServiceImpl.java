package com.Profpost.service.impl;

import com.Profpost.model.entity.Category;
import com.Profpost.repository.CategoryRepository;
import com.Profpost.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Category findById(int id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
    }

    @Transactional
    @Override
    public Category Create(Category category) {
        category.setCreatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category Update(Integer id, Category category) {
        Category categoryFromDb = findById(id);  // Verificar que la categoría existe
        categoryFromDb.setName(category.getName());
        categoryFromDb.setDescription(category.getDescription());
        categoryFromDb.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(categoryFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Category category = findById(id);  // Verificar que la categoría existe
        categoryRepository.delete(category);
    }
}
