package com.Profpost.dto;

import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PublicationDTO {
    private Integer id;
    private String title;
    private String content;
    private String video_url;
    private String coverPath;
    private String filePath;
    private Integer category_id;
    private Integer creator_id;
    private LocalDateTime schedulePublishAt;
}
