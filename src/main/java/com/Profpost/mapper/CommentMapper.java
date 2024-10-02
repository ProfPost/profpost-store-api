package com.Profpost.mapper;

import com.Profpost.dto.CommentCreateDTO;
import com.Profpost.dto.CommentDetailsDTO;
import com.Profpost.model.entity.Comment;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    private final ModelMapper modelMapper;

    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public CommentDetailsDTO toDetailsDTO(Comment comment) {
        CommentDetailsDTO commentDetailsDTO = modelMapper.map(comment, CommentDetailsDTO.class);
        commentDetailsDTO.setPublicationTitle(comment.getPublication().getTitle());
        return commentDetailsDTO;
    }

    public Comment toEntity(CommentCreateDTO commentCreateDTO) {
        return modelMapper.map(commentCreateDTO, Comment.class);
    }

    public CommentCreateDTO toEntity(Comment comment) {
        return modelMapper.map(comment, CommentCreateDTO.class);
    }
}
