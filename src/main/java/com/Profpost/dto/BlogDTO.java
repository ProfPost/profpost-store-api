package com.Profpost.dto;

import com.Profpost.model.entity.Blog;
import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BlogDTO {
    private String title;
    private String content;
    private Integer category_id;
    private Integer user_id;
    private LocalDateTime schedulePublishAt;

    public Blog toBlog(Category category, User user) {
        Blog blog = new Blog();
        blog.setTitle(this.title);
        blog.setContent(this.content);
        blog.setCategory(category);
        blog.setUser(user);
        blog.setSchedulePublishAt(this.schedulePublishAt);

        return blog;
    }
}
