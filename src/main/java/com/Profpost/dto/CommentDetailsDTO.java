package com.Profpost.dto;

import lombok.Data;

@Data
public class CommentDetailsDTO {
    private Integer id;
    private String content;

    private String publicationTitle;
}
