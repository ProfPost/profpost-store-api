package com.Profpost.service;

import com.Profpost.dto.PublicationCreateDTO;
import com.Profpost.dto.PublicationDetailsDTO;
import jakarta.mail.MessagingException;

import java.util.List;

public interface PublicationService {
    List<PublicationDetailsDTO> findAll();
    PublicationDetailsDTO create(PublicationCreateDTO publicationCreateDTO) throws MessagingException;
    PublicationDetailsDTO findById(Integer id);
    PublicationDetailsDTO update(Integer id, PublicationCreateDTO updatePublicationDTO);
    void delete(Integer id);
    List<PublicationDetailsDTO> findTop5PublicationsByCreatedAt();
    List<PublicationDetailsDTO> findByCreatorId(Integer id);
}
