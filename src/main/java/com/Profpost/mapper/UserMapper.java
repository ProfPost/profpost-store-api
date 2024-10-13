package com.Profpost.mapper;

import com.Profpost.dto.UserProfileDTO;
import com.Profpost.dto.UserRegistrationDTO;
import com.Profpost.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public User toUserEntity(UserRegistrationDTO userRegistrationDTO) {
        return modelMapper.map(userRegistrationDTO, User.class);
    }

    public UserProfileDTO toUserProfileDTO(User user) {
        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);

        if (user.getReader() != null) {
            userProfileDTO.setName(user.getReader().getName());
            userProfileDTO.setBiography(user.getReader().getBiography());
        }

        if (user.getCreator() != null) {
            userProfileDTO.setName(user.getCreator().getName());
            userProfileDTO.setBiography(user.getCreator().getBiography());
        }

        return userProfileDTO;
    }
}
