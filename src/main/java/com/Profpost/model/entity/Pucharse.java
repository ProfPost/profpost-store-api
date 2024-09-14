package com.Profpost.model.entity;

import com.Profpost.model.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pucharse")
public class Pucharse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private float total;

    @Enumerated(EnumType.STRING)
    private PaymentStatus payment_status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_pucharse_user"))
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "suscription_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_pucharse_suscription"))
    private Suscription suscription;

    @ManyToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_pucharse_donation"))
    private Donation donation;
}
