package com.Profpost.service;

import com.Profpost.dto.PublicationDTO;
import com.Profpost.model.entity.Publication;

import java.util.List;

public interface PublicationService {
    List<PublicationDTO> findAll();
    PublicationDTO create(PublicationDTO publicationDTO);
    PublicationDTO findById(Integer id);
    PublicationDTO update(Integer id, PublicationDTO updatePublicationDTO);
    void delete(Integer id);
}
