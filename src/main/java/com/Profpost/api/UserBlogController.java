package com.Profpost.api;

import com.Profpost.model.entity.Blog;
import com.Profpost.service.UserBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/blogs")
public class UserBlogController {
    private final UserBlogService userBlogService;

    @GetMapping
    public ResponseEntity<List<Blog>> list(){
        List<Blog> blogs = userBlogService.findAll();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Blog> create(@RequestBody Blog blogFromDTO){
        Blog createdBlog = userBlogService.create(blogFromDTO);
        return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> get(@PathVariable Integer id){
        Blog blog = userBlogService.findById(id);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> update(@PathVariable Integer id, @RequestBody Blog blogFromDTO){
        Blog updatedBlog = userBlogService.update(id, blogFromDTO);
        return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        userBlogService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
