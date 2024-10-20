package com.Profpost.service;

import com.Profpost.dto.PublicationCreateDTO;
import com.Profpost.dto.PublicationDetailsDTO;

import java.util.List;

public interface PublicationService {
    List<PublicationDetailsDTO> findAll();
    PublicationDetailsDTO create(PublicationCreateDTO publicationCreateDTO);
    PublicationDetailsDTO findById(Integer id);
    PublicationDetailsDTO update(Integer id, PublicationCreateDTO updatePublicationDTO);
    void delete(Integer id);
}
