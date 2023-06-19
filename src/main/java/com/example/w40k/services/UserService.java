package com.example.w40k.services;

import com.example.w40k.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    Optional<User> authenticate(String username, String secret);

    void save(User user);
}




