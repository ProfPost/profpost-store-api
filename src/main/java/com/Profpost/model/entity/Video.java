package com.Profpost.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "video")

public class Video {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(name = "duration")
    private float duration;

    @Column(name = "cover_path")
    private String coverPath;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_blog_category"))
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_video_usuario"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "playlist_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_video_playlist"))
    private Playlist playlist;
}
