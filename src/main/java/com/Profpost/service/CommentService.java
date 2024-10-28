package com.Profpost.service;

import com.Profpost.dto.CommentCreateDTO;
import com.Profpost.dto.CommentDetailsDTO;

import java.util.List;

public interface CommentService {
    List<CommentDetailsDTO> getAll();
    CommentDetailsDTO Create(CommentCreateDTO commentCreateDTO);
    void delete(Integer id);
}
