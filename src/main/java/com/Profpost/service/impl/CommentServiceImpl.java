package com.Profpost.service.impl;

import com.Profpost.dto.CommentCreateDTO;
import com.Profpost.dto.CommentDetailsDTO;
import com.Profpost.exception.ResourceNotFoundExcept;
import com.Profpost.mapper.CommentMapper;
import com.Profpost.model.entity.Comment;
import com.Profpost.model.entity.Publication;
import com.Profpost.repository.CommentRepository;
import com.Profpost.repository.PublicationRepository;
import com.Profpost.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PublicationRepository publicationRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDetailsDTO> getAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(commentMapper::toDetailsDTO)
                .toList();
    }

    @Transactional
    @Override
    public CommentDetailsDTO Create(CommentCreateDTO commentCreateDTO) {
        Publication publication = publicationRepository.findById(commentCreateDTO.getPublicationId())
                .orElseThrow(() -> new ResourceNotFoundExcept("La publicacion no existe con id: " + commentCreateDTO.getPublicationId()));
        Comment comment = commentMapper.toEntity(commentCreateDTO);
        comment.setPublication(publication);
        comment.setCreatedAt(LocalDateTime.now());
        return commentMapper.toDetailsDTO(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("El commentario no existe con id: " + id));
        commentRepository.delete(comment);
    }
}
