package com.Profpost.service.impl;

import com.Profpost.dto.PublicationDTO;
import com.Profpost.mapper.PublicationMapper;
import com.Profpost.model.entity.Category;
import com.Profpost.model.entity.Creator;
import com.Profpost.model.entity.Publication;
import com.Profpost.model.entity.User;
import com.Profpost.repository.*;
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
    private final CreatorRepository creatorRepository;
    private final CategoryRepository categoryRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final EmailService emailService;
    private final PublicationMapper publicationMapper;

    @Transactional(readOnly = true)
    @Override
    public List<PublicationDTO> findAll() {
        List<Publication> publications = publicationRepository.findAll();
        return publications.stream()
                .map(publicationMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public PublicationDTO create(PublicationDTO publicationDTO) {
        Creator creator = creatorRepository.findById(publicationDTO.getCreator_id())
                .orElseThrow(() -> new RuntimeException("Creator not found with id: " + publicationDTO.getCreator_id()));
        Category category = categoryRepository.findById(publicationDTO.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + publicationDTO.getCategory_id()));

        Publication publication = publicationMapper.toEntity(publicationDTO);
        publication.setTitle(publicationDTO.getTitle());
        publication.setContent(publicationDTO.getContent());
        publication.setVideo_url(publicationDTO.getVideo_url());
        publication.setCoverPath(publicationDTO.getCoverPath());
        publication.setFilePath(publicationDTO.getFilePath());
        publication.setCategory(category);
        publication.setCreator(creator);
        publication.setCreatedAt(LocalDateTime.now());

        if (publication.getSchedulePublishAt() != null && publication.getSchedulePublishAt().isAfter(LocalDateTime.now())) {
            publication.setPublished(false);
        } else {
            publication.setPublished(true);
        }

        Publication savedPublication = publicationRepository.save(publication);

        List<User> subscribers = subscriptionRepository.findAllSubscribersByCreatorId(creator.getId());
        for (User subscriber : subscribers) {
            emailService.sendNotification(
                    subscriber.getEmail(),
                    "Nueva publicación de " + creator.getName(),
                    "Se ha creado una nueva publicación titulada: " + savedPublication.getTitle()
            );
        }
        return publicationMapper.toDTO(savedPublication);
    }

    @Transactional(readOnly = true)
    @Override
    public PublicationDTO findById(Integer id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
        return publicationMapper.toDTO(publication);
    }

    @Transactional
    @Override
    public PublicationDTO update(Integer id, PublicationDTO updatePublicationDTO) {
        Publication publicationFromDb = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
        Category category = categoryRepository.findById(updatePublicationDTO.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + updatePublicationDTO.getCategory_id()));
        Creator creator = creatorRepository.findById(updatePublicationDTO.getCreator_id())
                .orElseThrow(() -> new RuntimeException("Creator not found with id: " + updatePublicationDTO.getCreator_id()));

        Publication publication = publicationMapper.toEntity(updatePublicationDTO);
        publication.setTitle(updatePublicationDTO.getTitle());
        publication.setContent(updatePublicationDTO.getContent());
        publication.setVideo_url(updatePublicationDTO.getVideo_url());
        publication.setCoverPath(updatePublicationDTO.getCoverPath());
        publication.setFilePath(updatePublicationDTO.getFilePath());
        publication.setCategory(category);
        publication.setCreator(creator);
        publication.setCreatedAt(LocalDateTime.now());

        Publication updatedPublication = publicationRepository.save(publicationFromDb);
        return publicationMapper.toDTO(updatedPublication);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
        publicationRepository.delete(publication);
    }
}
