package com.Profpost.dto;

import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.Publication;
import com.Profpost.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PublicationDTO {

    @NotBlank(message = "El título de la publicación es obligatorio.")
    @Size(max = 30, message = "El título debe tener menos de 30 caracteres.")
    private String title;

    @Size(max = 150, message = "El contenido debe tener como máximo 150 caracteres")
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
