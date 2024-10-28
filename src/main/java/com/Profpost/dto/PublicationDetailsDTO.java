package com.Profpost.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublicationDetailsDTO {
    private Integer id;
    private String title;
    private String content;
    private String video_url;
    private String filePath;
    private String visibility;
    private String categoryname;
    private String creatorname;

    private LocalDateTime schedulePublishAt;
}
