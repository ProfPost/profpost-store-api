package com.Profpost.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "video_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_category_video"))
    private Video video;

    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_category_blog"))
    private Blog blog;
}
