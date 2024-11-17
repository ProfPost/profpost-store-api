package com.Profpost.service;

import com.Profpost.dto.*;
import com.Profpost.model.entity.User;

import java.util.List;

public interface UserService {
    List<UserProfileDTO> findAll();
    List<UserSearchDTO> searchUsersByName(String userName);

    AuthResponseDTO login(LoginDTO loginDTO);
    UserProfileDTO registerReader(UserRegistrationDTO registrationDTO);
    UserProfileDTO registerCreator(UserRegistrationDTO registrationDTO);
    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfileById(Integer id);

    void delete(Integer id);
}
