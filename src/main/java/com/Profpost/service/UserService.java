package com.Profpost.service;

import com.Profpost.dto.UserDTO;
import com.Profpost.dto.UserProfileDTO;
import com.Profpost.dto.UserRegistrationDTO;
import com.Profpost.model.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO registerUser(UserDTO userDTO);
    UserDTO findById(Integer id);
    User findByEmail(String email);
    UserDTO update(Integer id, UserDTO updatedUser);
    void delete(Integer id);
    public boolean checkPassword(String inputPassword, String storedPassword);

    // Registrar a reader
    UserProfileDTO registerReader(UserRegistrationDTO registrationDTO);

    // Registrar a creator
    UserProfileDTO registerCreator(UserRegistrationDTO registrationDTO);

    // Actualizar el perfil de usuario
    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);

    // Obtener perfil del usuario por id
    UserProfileDTO getUserProfileById(Integer id);
}
