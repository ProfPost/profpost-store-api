package com.Profpost.dto;

import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.User;
import com.Profpost.model.entity.Video;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoDTO {
    public String title;
    public String url;
    public Integer category_id;
    public Integer user_id;
    public LocalDateTime schedulePublishAt;

    public Video toVideo(Category category, User user) {
        Video video = new Video();
        video.setCategory(category);
        video.setUser(user);
        video.setTitle(this.title);
        video.setUrl(this.url);
        video.setSchedulePublishAt(this.schedulePublishAt);

        return video;
    }
}
