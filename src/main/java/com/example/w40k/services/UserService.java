package com.example.w40k.services;

import com.example.w40k.models.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User save(User user);

    Optional<User> findById(Long id);
}
