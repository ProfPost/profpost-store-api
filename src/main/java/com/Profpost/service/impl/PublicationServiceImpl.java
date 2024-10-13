package com.Profpost.service.impl;

import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.Publication;
import com.Profpost.model.entity.User;
import com.Profpost.repository.PublicationRepository;
import com.Profpost.repository.CategoryRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Publication> findAll() {
        return publicationRepository.findAll();
    }

    @Override
    public Publication create(Publication publication) {
        Category category = categoryRepository.findById(publication.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found" + publication.getCategory().getId()));
        User user = userRepository.findById(publication.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found" + publication.getUser().getId()));

        publication.setCategory(category);
        publication.setUser(user);
        publication.setCreatedAt(LocalDateTime.now());

        if (publication.getSchedulePublishAt() != null && publication.getSchedulePublishAt().isAfter(LocalDateTime.now())) {
            publication.setPublished(false);
        } else {
            publication.setPublished(true);
        }

        return publicationRepository.save(publication);
    }

    @Transactional(readOnly = true)
    @Override
    public Publication findById(Integer id) {
        return publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
    }

    @Transactional
    @Override
    public Publication update(Integer id, Publication updatePublication) {
        Publication publicationFromDb = findById(id);

        Category category = categoryRepository.findById(updatePublication.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found" + updatePublication.getCategory().getId()));
        User user = userRepository.findById(updatePublication.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found" + updatePublication.getUser().getId()));

        publicationFromDb.setTitle(updatePublication.getTitle());
        publicationFromDb.setContent(updatePublication.getContent());
        publicationFromDb.setVideo_url(updatePublication.getVideo_url());
        publicationFromDb.setCoverPath(updatePublication.getCoverPath());
        publicationFromDb.setFilePath(updatePublication.getFilePath());
        publicationFromDb.setCategory(category);
        publicationFromDb.setUser(user);
        publicationFromDb.setUpdatedAt(LocalDateTime.now());
        return publicationRepository.save(publicationFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
        publicationRepository.delete(publication);
    }
}
