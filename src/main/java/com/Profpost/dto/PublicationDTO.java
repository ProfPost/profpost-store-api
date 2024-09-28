package com.Profpost.dto;

import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.Publication;
import com.Profpost.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PublicationDTO {
    private String title;
    private String content;
    private String video_url;
    private Integer category_id;
    private Integer user_id;
    private LocalDateTime schedulePublishAt;

    public Publication toPublication(Category category, User user) {
        Publication publication = new Publication();
        publication.setTitle(this.title);
        publication.setContent(this.content);
        publication.setVideo_url(this.video_url);
        publication.setCategory(category);
        publication.setUser(user);
        publication.setSchedulePublishAt(this.schedulePublishAt);

        return publication;
    }
}
