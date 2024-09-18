package com.Profpost.api;

import com.Profpost.model.entity.Category;
import com.Profpost.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;




@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Integer id){
        Category category = categoryService.findById(id);
        return new ResponseEntity<Category>(category, HttpStatus.OK);

    }
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category newCategory = categoryService.Create(category);
        return new ResponseEntity<Category>(newCategory,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Integer id, @RequestBody Category category){
        Category updateCategory = categoryService.Update(id, category);
        return new ResponseEntity<Category>(updateCategory,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") Integer id){
        categoryService.delete(id);
        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);

    }

}