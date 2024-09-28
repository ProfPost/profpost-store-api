package com.Profpost.scheduler;

import com.Profpost.model.entity.Publication;
import com.Profpost.repository.PublicationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PublicationScheduler {
    private final PublicationRepository publicationRepository;

    public PublicationScheduler(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void publishScheduledPublication() {
        List<Publication> scheduledPublication = publicationRepository.findAllByIsPublishedFalseAndSchedulePublishAtBefore(LocalDateTime.now());

        for (Publication publication : scheduledPublication) {
            publication.setPublished(true);
            publicationRepository.save(publication);
        }
    }
}
