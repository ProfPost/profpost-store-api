package com.Profpost.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "publications")

public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "url", unique = true)
    private String video_url;

    @Column(name = "cover_path")
    private String coverPath;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "schedulePublishAt")
    private LocalDateTime schedulePublishAt;

    @Column(name = "isPublished", nullable = false)
    private boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_publication_category"))
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_publication_user"))
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "playlist_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_publication_playlist"))
    private Playlist playlist;
}
