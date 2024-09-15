package com.Profpost.service;

import com.Profpost.model.entity.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User create(User user);
    User findById(Integer id);
    User update(Integer id, User updatedUser);
    void delete(Integer id);
}
