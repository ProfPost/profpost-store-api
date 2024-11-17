package com.Profpost.service.impl;

import com.Profpost.integration.notification.email.dto.Mail;
import com.Profpost.integration.notification.email.service.EmailService;
import com.Profpost.dto.PublicationCreateDTO;
import com.Profpost.dto.PublicationDetailsDTO;
import com.Profpost.mapper.PublicationMapper;
import com.Profpost.model.entity.*;
import com.Profpost.model.enums.SubscriptionState;
import com.Profpost.repository.*;
import com.Profpost.service.PublicationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository publicationRepository;
    private final CreatorRepository creatorRepository;
    private final CategoryRepository categoryRepository;
    private final EmailService emailService;
    private final PublicationMapper publicationMapper;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String mailFrom;

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
    public PublicationDetailsDTO create(PublicationCreateDTO publicationCreateDTO) throws MessagingException {
        if (publicationCreateDTO.getCreator_id() == null) {
            throw new IllegalArgumentException("El ID del creador no puede ser nulo");
        }
        if (publicationCreateDTO.getCategory_id() == null) {
            throw new IllegalArgumentException("El ID de la categoría no puede ser nulo");
        }

        Category category = categoryRepository.findById(publicationCreateDTO.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + publicationCreateDTO.getCategory_id()));

        User user = userRepository.findById(publicationCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + publicationCreateDTO.getUserId()));
        Creator creator = creatorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Creador no encontrado con el userId proporcionado"));

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

        List<Reader> subscribers = publicationRepository.findReadersByCreatorIdAndSubscriptionState(
                user.getId(), SubscriptionState.SUBSCRIBE);
        for (Reader subscriber : subscribers) {
            String email = subscriber.getUser().getEmail();

            Map<String, Object> model = new HashMap<>();
            String publicationUrl = "http://localhost:4200/creator/publications-catalog";
            model.put("creatorName", creator.getName());
            model.put("publicationTitle", savedPublication.getTitle());
            model.put("publicationContent", savedPublication.getContent());
            model.put("publicationDate", savedPublication.getCreatedAt().toString());
            model.put("publicationUrl", publicationUrl);
            model.put("user", subscriber.getName());

            Mail mail = emailService.createMail(
                    email,
                    "Nueva publicación",
                    model,
                    mailFrom
            );
            emailService.sendMail(mail, "email/publication-template");
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
        // Buscar la publicación en la base de datos
        Publication publicationFromDb = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));

        // Buscar y asignar la categoría
        Category category = categoryRepository.findById(updatePublicationDTO.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + updatePublicationDTO.getCategory_id()));

        // Buscar y asignar el creador
        Creator creator = creatorRepository.findById(updatePublicationDTO.getCreator_id())
                .orElseThrow(() -> new RuntimeException("Creator not found with id: " + updatePublicationDTO.getCreator_id()));

        // Actualizar otros campos de la publicación
        publicationFromDb.setTitle(updatePublicationDTO.getTitle());
        publicationFromDb.setContent(updatePublicationDTO.getContent());
        publicationFromDb.setVideo_url(updatePublicationDTO.getVideo_url());
        publicationFromDb.setFilePath(updatePublicationDTO.getFilePath());
        publicationFromDb.setVisibility(updatePublicationDTO.getVisibility());
        publicationFromDb.setCategory(category);
        publicationFromDb.setCreator(creator);
        publicationFromDb.setCreatedAt(LocalDateTime.now());

        // Guardar la publicación actualizada en la base de datos
        Publication updatedPublication = publicationRepository.save(publicationFromDb);

        // Convertir a DTO y devolver la respuesta
        return publicationMapper.toDetailsDTO(updatedPublication);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
        publicationRepository.delete(publication);
    }

    @Override
    public List<PublicationDetailsDTO> findTop5PublicationsByCreatedAt(){
        return publicationRepository.findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(publicationMapper::toDetailsDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PublicationDetailsDTO> findByCreatorId(Integer creatorId) {
        List<Publication> publications = publicationRepository.findByCreatorId(creatorId);
        return publications.stream()
                .map(publicationMapper::toDetailsDTO)
                .collect(Collectors.toList());
    }
}
