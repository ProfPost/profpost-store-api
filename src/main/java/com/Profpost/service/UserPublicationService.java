package com.Profpost.service;

import com.Profpost.model.entity.Publication;

import java.util.List;

public interface UserPublicationService {
    List<Publication> findAll();
    Publication create(Publication publication);
    Publication findById(Integer id);
    Publication update(Integer id, Publication updatePublication);
    void delete(Integer id);
}
