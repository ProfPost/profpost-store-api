package com.Profpost.model.entity;

import com.Profpost.model.enums.SubscriptionState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_date")
    private LocalDateTime starDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private SubscriptionState subscriptionState;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_subscription_user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_subscription_creator"))
    @JsonIgnore
    private User creator;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;
}
