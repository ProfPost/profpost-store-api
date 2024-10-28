package com.Profpost.service.impl;


import com.Profpost.integration.notification.email.service.EmailService;
import com.Profpost.dto.PublicationCreateDTO;
import com.Profpost.dto.PublicationDetailsDTO;
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
    public List<PublicationDetailsDTO> findAll() {
        List<Publication> publications = publicationRepository.findAll();
        return publications.stream()
                .map(publicationMapper::toDetailsDTO)
                .toList();
    }

    @Transactional
    @Override
    public PublicationDetailsDTO create(PublicationCreateDTO publicationCreateDTO) {
        if (publicationCreateDTO.getCreator_id() == null) {
            throw new IllegalArgumentException("El ID del creador no puede ser nulo");
        }
        if (publicationCreateDTO.getCategory_id() == null) {
            throw new IllegalArgumentException("El ID de la categoría no puede ser nulo");
        }

        Creator creator = creatorRepository.findById(publicationCreateDTO.getCreator_id())
                .orElseThrow(() -> new RuntimeException("Creator not found with id: " + publicationCreateDTO.getCreator_id()));
        Category category = categoryRepository.findById(publicationCreateDTO.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + publicationCreateDTO.getCategory_id()));

        Publication publication = publicationMapper.toEntity(publicationCreateDTO);
        publication.setTitle(publicationCreateDTO.getTitle());
        publication.setContent(publicationCreateDTO.getContent());
        publication.setVideo_url(publicationCreateDTO.getVideo_url());
        publication.setFilePath(publicationCreateDTO.getFilePath());
        publication.setVisibility(publicationCreateDTO.getVisibility());
        publication.setCategory(category);
        publication.setCreator(creator);
        publication.setCreatedAt(LocalDateTime.now());

        if (publication.getSchedulePublishAt() != null && publication.getSchedulePublishAt().isAfter(LocalDateTime.now())) {
            publication.setPublished(false);
        } else {
            publication.setPublished(true);
        }

        Publication savedPublication = publicationRepository.save(publication);

        List<User> subscribers = subscriptionRepository.getSubscribersByCreatorId(creator.getId());
        for (User subscriber : subscribers) {
            emailService.sendNotification(
                    subscriber.getEmail(),
                    "Nueva publicación de " + creator.getName(),
                    "Se ha creado una nueva publicación titulada: " + savedPublication.getTitle()
            );
        }
        return publicationMapper.toDetailsDTO(savedPublication);
    }

    @Transactional(readOnly = true)
    @Override
    public PublicationDetailsDTO findById(Integer id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
        return publicationMapper.toDetailsDTO(publication);
    }

    @Transactional
    @Override
    public PublicationDetailsDTO update(Integer id, PublicationCreateDTO updatePublicationDTO) {
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
        publication.setFilePath(updatePublicationDTO.getFilePath());
        publication.setVisibility(updatePublicationDTO.getVisibility());
        publication.setCategory(category);
        publication.setCreator(creator);
        publication.setCreatedAt(LocalDateTime.now());

        Publication updatedPublication = publicationRepository.save(publicationFromDb);
        return publicationMapper.toDetailsDTO(updatedPublication);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
        publicationRepository.delete(publication);
    }
}
