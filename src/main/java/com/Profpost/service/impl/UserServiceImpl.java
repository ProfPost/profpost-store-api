package com.Profpost.service.impl;

import com.Profpost.dto.UserDTO;
import com.Profpost.exception.ResourceNotFoundExcept;
import com.Profpost.mapper.UserMapper;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.Role;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        User user = userMapper.toEntity(userDTO);
        user.setRole(Role.READER);
        user.setCreatedAt(LocalDateTime.now());
        User newUser = userRepository.save(user);
        return userMapper.toDTO(newUser);
    }

    @Transactional
    @Override
    public UserDTO findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("Usuario no encontrado"));
        return userMapper.toDTO(user);
    }

    @Transactional
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExcept("Email no encontrado"));
    }

    @Transactional
    @Override
    public UserDTO update(Integer id, UserDTO updatedUserDTO) {
        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("Usuario no encontrado"));
        userFromDb.setName(updatedUserDTO.getName());
        userFromDb.setEmail(updatedUserDTO.getEmail());
        userFromDb.setPassword(updatedUserDTO.getPassword());
        userFromDb.setBiography(updatedUserDTO.getBiography());
        userFromDb.setUpdatedAt(LocalDateTime.now());
        if (updatedUserDTO.getRole() == Role.CREATOR) {
            userFromDb.setRole(Role.CREATOR);
        }
        User updatedUser = userRepository.save(userFromDb);
        return userMapper.toDTO(updatedUser);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("El usuario con ID"+id+"no fue encontrado"));
        userRepository.delete(user);
    }

    @Override
    public boolean checkPassword(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }
}
