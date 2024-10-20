package com.Profpost.mapper;

import com.Profpost.dto.AuthResponseDTO;
import com.Profpost.dto.LoginDTO;
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

    public User toUserEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

    public AuthResponseDTO toAuthResponseDTO(User user, String token) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);

        String name = (user.getReader() != null) ? user.getReader().getName()
                : (user.getCreator() != null) ? user.getCreator().getName()
                : "Admin";

        authResponseDTO.setName(name);
        authResponseDTO.setRole(user.getRole().getName().name());

        return authResponseDTO;
    }
}
