package com.Profpost.service.impl;

import com.Profpost.model.entity.User;
import com.Profpost.repository.UserRepository;
import com.Profpost.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                        .orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Transactional
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    @Override
    public User update(Integer id, User updatedUser) {
        User userFromDb = findById(id);
        userFromDb.setName(updatedUser.getName());
        userFromDb.setEmail(updatedUser.getEmail());
        userFromDb.setPassword(updatedUser.getPassword());
        userFromDb.setBiography(updatedUser.getBiography());
        userFromDb.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(userFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }
}
