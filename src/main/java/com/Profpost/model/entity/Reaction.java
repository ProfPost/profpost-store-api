package com.Profpost.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reaction")

public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value", nullable = false)
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_reaction_publication"))
    private Publication publication;
}
