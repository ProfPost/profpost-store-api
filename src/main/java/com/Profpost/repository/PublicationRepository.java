package com.Profpost.repository;

import com.Profpost.model.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer> {
    List<Publication> findAllByIsPublishedFalseAndSchedulePublishAtBefore(LocalDateTime now);
}
