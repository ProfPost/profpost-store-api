package com.Profpost.mapper;

import com.Profpost.dto.PlaylistDTO;
import com.Profpost.model.entity.Playlist;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlaylistMapper {
    private final ModelMapper modelMapper;

    public PlaylistMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PlaylistDTO toDTO(Playlist playlist) {
        return modelMapper.map(playlist, PlaylistDTO.class);
    }

    public Playlist toEntity(PlaylistDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Playlist.class);
    }
}
