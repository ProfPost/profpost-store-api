package com.Profpost.service;

import com.Profpost.dto.CommentCreateDTO;
import com.Profpost.dto.CommentDetailsDTO;
import com.Profpost.model.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentService {
    List<CommentDetailsDTO> getAll();
    CommentDetailsDTO Create(CommentCreateDTO commentCreateDTO);
    void delete(Integer id);
}
