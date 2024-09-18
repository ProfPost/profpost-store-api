package com.Profpost.service.impl;

import com.Profpost.model.entity.Blog;
import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.SubscriptionState;
import com.Profpost.repository.BlogRepository;
import com.Profpost.repository.CategoryRepository;
import com.Profpost.repository.SubscriptionRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.UserBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserBlogServiceImpl implements UserBlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog create(Blog blog) {
        Category category = categoryRepository.findById(blog.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found" + blog.getCategory().getId()));
        User user = userRepository.findById(blog.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found" + blog.getUser().getId()));

        blog.setCategory(category);
        blog.setUser(user);
        blog.setCreatedAt(LocalDateTime.now());

        if (blog.getSchedulePublishAt() != null && blog.getSchedulePublishAt().isAfter(LocalDateTime.now())) {
            blog.setPublished(false);
        } else {
            blog.setPublished(true);
        }

        blogRepository.save(blog);

        List<Subscription> subscriptions = subscriptionRepository.findByCreator(blog.getUser());

        for (Subscription subscription : subscriptions) {
            if (subscription.getSubscriptionState() == SubscriptionState.SUBSCRIBE){
                String toEmail = subscription.getUser().getEmail();
                String subject = "Nueva publicación subido por " + blog.getUser().getName();
                String body = "El creador " + blog.getUser().getName() + " ha subido nuevo blog: " + blog.getTitle();
                emailService.sendNotification(toEmail, subject, body);
            }
        }

        return blogRepository.save(blog);
    }

    @Transactional(readOnly = true)
    @Override
    public Blog findById(Integer id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
    }

    @Transactional
    @Override
    public Blog update(Integer id, Blog updateBlog) {
        Blog blogFromDb = findById(id);

        Category category = categoryRepository.findById(updateBlog.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found" + updateBlog.getCategory().getId()));
        User user = userRepository.findById(updateBlog.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found" + updateBlog.getUser().getId()));

        blogFromDb.setTitle(updateBlog.getTitle());
        blogFromDb.setContent(updateBlog.getContent());
        blogFromDb.setCoverPath(updateBlog.getCoverPath());
        blogFromDb.setFilePath(updateBlog.getFilePath());
        blogFromDb.setCategory(category);
        blogFromDb.setUser(user);
        blogFromDb.setUpdatedAt(LocalDateTime.now());

        return blogRepository.save(blogFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
        blogRepository.delete(blog);
    }
}
