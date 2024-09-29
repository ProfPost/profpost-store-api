package com.Profpost.api;

import com.Profpost.dto.CategoryDTO;
import com.Profpost.model.entity.Category;
import com.Profpost.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Integer id){
        CategoryDTO category = categoryService.findById(id);
        return new ResponseEntity<CategoryDTO>(category, HttpStatus.OK);

    }
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO newCategory = categoryService.Create(categoryDTO);
        return new ResponseEntity<CategoryDTO>(newCategory,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") Integer id,@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO updateCategory = categoryService.Update(id, categoryDTO);
        return new ResponseEntity<CategoryDTO>(updateCategory,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("id") Integer id){
        categoryService.delete(id);
        return new ResponseEntity<CategoryDTO>(HttpStatus.NO_CONTENT);

    }

}
