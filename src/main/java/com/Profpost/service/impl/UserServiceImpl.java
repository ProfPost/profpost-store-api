package com.Profpost.service.impl;

import com.Profpost.dto.UserProfileDTO;
import com.Profpost.dto.UserRegistrationDTO;
import com.Profpost.exception.ResourceNotFoundExcept;
import com.Profpost.mapper.UserMapper;
import com.Profpost.model.entity.Creator;
import com.Profpost.model.entity.Reader;
import com.Profpost.model.entity.Role;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.ERole;
import com.Profpost.repository.CreatorRepository;
import com.Profpost.repository.ReaderRepository;
import com.Profpost.repository.RoleRepository;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ReaderRepository readerRepository;
    private final CreatorRepository creatorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserProfileDTO registerReader(UserRegistrationDTO registrationDTO) {
        return registerUserWithRole(registrationDTO, ERole.READER);
    }

    @Transactional
    @Override
    public UserProfileDTO registerCreator(UserRegistrationDTO registrationDTO) {
        return registerUserWithRole(registrationDTO, ERole.CREATOR);
    }

    @Transactional
    @Override
    public UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("Usuario no encontrado"));

        // Solo actualizamos el nombre si se ha proporcionado
        if (userProfileDTO.getName() != null && !userProfileDTO.getName().isEmpty()) {
            boolean existsAsReader = readerRepository.existsByNameAndUserIdNot(userProfileDTO.getName(), id);
            boolean existsAsCreator = creatorRepository.existsByNameAndUserIdNot(userProfileDTO.getName(), id);

            if (existsAsReader || existsAsCreator) {
                throw new IllegalArgumentException("Ya existe un usuario con el mismo username");
            }

            if (user.getReader() != null) {
                user.getReader().setName(userProfileDTO.getName());
            }

            if (user.getCreator() != null) {
                user.getCreator().setName(userProfileDTO.getName());
            }
        }

        // Actualizamos la biografía si está presente
        if (userProfileDTO.getBiography() != null && !userProfileDTO.getBiography().isEmpty()) {
            if (user.getReader() != null) {
                user.getReader().setBiography(userProfileDTO.getBiography());
                user.getReader().setUpdatedAt(LocalDateTime.now());
            }

            if (user.getCreator() != null) {
                user.getCreator().setBiography(userProfileDTO.getBiography());
                user.getCreator().setUpdatedAt(LocalDateTime.now());
            }
        }

        // Guardamos los cambios
        User updatedUser = userRepository.save(user);

        return userMapper.toUserProfileDTO(updatedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfileDTO getUserProfileById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcept("Usuario no encontrado"));

        return userMapper.toUserProfileDTO(user);
    }

    private UserProfileDTO registerUserWithRole(UserRegistrationDTO registrationDTO, ERole roleEnum) {

        // Verificar el email ya esta registradp o si ya existe un usuario con el mismo nombre
        boolean existsByEmail = userRepository.existsByEmail(registrationDTO.getEmail());
        boolean existsAsReader = readerRepository.existsByName(registrationDTO.getName());
        boolean existsAsCreator = creatorRepository.existsByName(registrationDTO.getName());

        if (existsByEmail) {
            throw new IllegalArgumentException("El email ya esta registrado");
        }

        if (existsAsReader || existsAsCreator) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo username");
        }

        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new ResourceNotFoundExcept("El Role no existe"));

        registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        User user = userMapper.toUserEntity(registrationDTO);
        user.setRole(role);

        if (roleEnum == ERole.READER){
            Reader reader = new Reader();
            reader.setName(registrationDTO.getName());
            reader.setBiography(registrationDTO.getBiography());
            reader.setCreatedAt(LocalDateTime.now());
            reader.setUser(user);
            user.setReader(reader);
        }else if(roleEnum == ERole.CREATOR){
            Creator creator = new Creator();
            creator.setName(registrationDTO.getName());
            creator.setBiography(registrationDTO.getBiography());
            creator.setCreatedAt(LocalDateTime.now());
            creator.setUser(user);
            user.setCreator(creator);
        }

        User savedUser = userRepository.save(user);

        return userMapper.toUserProfileDTO(savedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserProfileDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserProfileDTO)
                .toList();
    }

    @Transactional
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExcept("Email no encontrado"));
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
