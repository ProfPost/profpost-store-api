package com.Profpost.service.impl;

import com.Profpost.dto.CategoryDTO;
import com.Profpost.mapper.CategoryMapper;
import com.Profpost.model.entity.Category;
import com.Profpost.repository.CategoryRepository;
import com.Profpost.service.CategoryService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
        .map(categoryMapper::toDTO)
        .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDTO findById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoria con ID"+id+"no fue encontrada"));
        return categoryMapper.toDTO(category);
    }

    @Transactional
    @Override
    public CategoryDTO Create(CategoryDTO categoryDTO) {
        categoryRepository.findByName(categoryDTO.getName())
                .ifPresent(existingCategory -> {
                    throw new RuntimeException("La categoria ya existe con el mismo nombre");});

        Category category = categoryMapper.toEntity(categoryDTO);
        category.setCreatedAt(LocalDateTime.now());
        category = categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Transactional
    @Override
    public CategoryDTO Update(Integer id, CategoryDTO updateCategoryDTO) {
        Category categoryFromDb = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoria con ID" + id + "no fue encontrada"));

        categoryRepository.findByName(updateCategoryDTO.getName())
                .filter(existingCategory -> !existingCategory.getName().equals(id))
                .ifPresent(existingCategory -> {
                    throw new RuntimeException("Ya existe otra categoria con el mismo nombre");

                });
        categoryFromDb.setName(updateCategoryDTO.getName());
        categoryFromDb.setDescription(updateCategoryDTO.getDescription());
        categoryFromDb.setUpDatedAt(LocalDateTime.now());

        categoryFromDb = categoryRepository.save(categoryFromDb);
        return categoryMapper.toDTO(categoryFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoria con ID"+id+"no fue encontrada"));
        categoryRepository.delete(category);

    }
}
