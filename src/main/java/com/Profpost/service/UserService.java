package com.Profpost.service;

import com.Profpost.dto.AuthResponseDTO;
import com.Profpost.dto.LoginDTO;
import com.Profpost.dto.UserProfileDTO;
import com.Profpost.dto.UserRegistrationDTO;
import com.Profpost.model.entity.User;

import java.util.List;

public interface UserService {
    List<UserProfileDTO> findAll();
    User findByEmail(String email);

    AuthResponseDTO login(LoginDTO loginDTO);
    UserProfileDTO registerReader(UserRegistrationDTO registrationDTO);
    UserProfileDTO registerCreator(UserRegistrationDTO registrationDTO);
    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfileById(Integer id);

    void delete(Integer id);
    public boolean checkPassword(String inputPassword, String storedPassword);
}
