package com.Profpost.service.impl;

import com.Profpost.dto.UserDTO;
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

    @Override
    public UserProfileDTO registerReader(UserRegistrationDTO registrationDTO) {
        return registerUserWithRole(registrationDTO, ERole.READER);
    }

    @Override
    public UserProfileDTO registerCreator(UserRegistrationDTO registrationDTO) {
        return registerUserWithRole(registrationDTO, ERole.CREATOR);
    }

    @Override
    public UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO) {
        return null;
    }

    @Override
    public UserProfileDTO getUserProfileById(Integer id) {
        return null;
    }

    private UserProfileDTO registerUserWithRole(UserRegistrationDTO registrationDTO, ERole roleEnum) {

        // Verificar el email ya esta registradp o si ya existe uun usuario con el mismo nombre
        boolean existsByEmail = userRepository.existsByEmail(registrationDTO.getEmail());
        boolean existsAsReader = readerRepository.existsByName(registrationDTO.getName());
        boolean existsAsCreator = creatorRepository.existsByName(registrationDTO.getName());

        if (existsByEmail) {
            throw new IllegalArgumentException("El email ya esta registrado");
        }

        if (existsAsReader && existsAsCreator) {
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
        user.setRole(ERole.READER);
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
        if (updatedUserDTO.getRole() == ERole.CREATOR) {
            userFromDb.setRole(ERole.CREATOR);
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
