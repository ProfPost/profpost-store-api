package com.Profpost.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "coments")

public class Coment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_coment_publication"))
    private Publication publication;

}
