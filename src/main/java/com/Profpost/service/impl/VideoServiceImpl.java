package com.Profpost.service.impl;

import com.Profpost.model.entity.Subscription;
import com.Profpost.model.entity.Video;
import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.User;

import com.Profpost.model.enums.SubscriptionState;
import com.Profpost.repository.SubscriptionRepository;
import com.Profpost.repository.VideoRepository;
import com.Profpost.repository.CategoryRepository;
import com.Profpost.repository.UserRepository;

import com.Profpost.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service

public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Transactional
    @Override
    public Video create(Video video) {
        Category category = categoryRepository.findById(video.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found" + video.getCategory().getId()));
        User user = userRepository.findById(video.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found" + video.getUser().getId()));
        video.setCategory(category);
        video.setUser(user);
        video.setCreatedAt(LocalDateTime.now());

        if(video.getSchedulePublishAt() != null && video.getSchedulePublishAt().isAfter(LocalDateTime.now())) {
            video.setPublished(false);
        }
        else {
            video.setPublished(true);
        }

        videoRepository.save(video);

        List<Subscription> subscriptions = subscriptionRepository.findByCreator(video.getUser());

        for (Subscription subscription : subscriptions) {
            if (subscription.getSubscriptionState() == SubscriptionState.SUBSCRIBE){
                String toEmail = subscription.getUser().getEmail();
                String subject = "Nuevo video subido por " + video.getUser().getName();
                String body = "El creador " + video.getUser().getName() + " ha subido nuevo blog: " + video.getTitle();
                emailService.sendNotification(toEmail, subject, body);
            }
        }

        return videoRepository.save(video);
    }

    @Transactional(readOnly = true)
    @Override
    public Video findById(Integer id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
    }

    @Transactional
    @Override
    public Video update(Integer id, Video updatedVideo) {
        Video videoFromDb = findById(id);

        Category category = categoryRepository.findById(updatedVideo.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found" + updatedVideo.getCategory().getId()));
        User user = userRepository.findById(updatedVideo.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found" + updatedVideo.getUser().getId()));

        videoFromDb.setTitle(updatedVideo.getTitle());
        videoFromDb.setUrl(updatedVideo.getUrl());
        videoFromDb.setDuration(updatedVideo.getDuration());
        videoFromDb.setCoverPath(updatedVideo.getCoverPath());
        videoFromDb.setFilePath(updatedVideo.getFilePath());
        videoFromDb.setUpdatedAt(LocalDateTime.now());
        videoFromDb.setCategory(category);
        videoFromDb.setUser(user);

        return videoRepository.save(videoFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
        videoRepository.delete(video);
    }
}