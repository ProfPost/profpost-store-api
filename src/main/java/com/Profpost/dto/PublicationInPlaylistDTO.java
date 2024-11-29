package com.Profpost.dto;

import lombok.Data;

@Data
public class PublicationInPlaylistDTO {
    private Integer id;
    private String title;
    private String content;
    private String video_url;
    private String filePath;
}
