package com.Profpost.scheduler;

import com.Profpost.model.entity.Video;
import com.Profpost.repository.VideoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VideoScheduler {
    private final VideoRepository videoRepository;

    public VideoScheduler(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Scheduled(fixedRate = 6000)
    public void publishSchedulerVideos(){
        List<Video> videos = videoRepository.findAllByIsPublishedFalseAndSchedulePublishAtBefore(LocalDateTime.now());

        for (Video video : videos){
            video.setPublished(true);
            videoRepository.save(video);
        }
    }
}
