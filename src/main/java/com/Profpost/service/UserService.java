package com.Profpost.service;

import com.Profpost.dto.UserDTO;
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
}
