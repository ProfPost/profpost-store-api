package com.Profpost.repository;

import com.Profpost.model.entity.Publication;
import com.Profpost.model.entity.Reader;
import com.Profpost.model.enums.SubscriptionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer> {
    List<Publication> findAllByIsPublishedFalseAndSchedulePublishAtBefore(LocalDateTime now);
    List<Publication> findTop5ByOrderByCreatedAtDesc();
    List<Publication> findByCreatorId(Integer creatorId);

    @Query("SELECT s.user.reader FROM Subscription s WHERE s.creator.id = :creatorId AND s.subscriptionState = :state")
    List<Reader> findReadersByCreatorIdAndSubscriptionState(@Param("creatorId") Integer creatorId, @Param("state") SubscriptionState state);
}
