package com.Profpost.api;

import com.Profpost.dto.BlogDTO;
import com.Profpost.model.entity.Blog;
import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.Role;
import com.Profpost.service.CategoryService;
import com.Profpost.service.UserBlogService;
import com.Profpost.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blogs")
public class UserBlogController {
    private final UserBlogService userBlogService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Blog>> list(){
        List<Blog> blogs = userBlogService.findAll();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> get(@PathVariable Integer id){
        Blog blog = userBlogService.findById(id);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @PostMapping("/creators")
    public ResponseEntity<Blog> create(@RequestBody BlogDTO blogDTO){
        User user = userService.findById(blogDTO.getUser_id());
        Category category = categoryService.findById(blogDTO.getCategory_id());

        if(user.getRole() != Role.CREATOR){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Blog blog = userBlogService.create(blogDTO.toBlog(category, user));
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @PutMapping("/creators/{id}")
    public ResponseEntity<Blog> update(@PathVariable Integer id, @RequestBody BlogDTO blogDTO){
        Blog existingBlog = userBlogService.findById(id);

        if (blogDTO.getCategory_id() != null) {
            Category category = categoryService.findById(blogDTO.getCategory_id());
            existingBlog.setCategory(category);
        }

        existingBlog.setTitle(blogDTO.getTitle());
        existingBlog.setContent(blogDTO.getContent());
        existingBlog.setSchedulePublishAt(blogDTO.getSchedulePublishAt());
        existingBlog.setUpdatedAt(LocalDateTime.now());

        Blog updatedBlog = userBlogService.update(id, existingBlog);
        return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
    }

    @DeleteMapping("/creators/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        userBlogService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
