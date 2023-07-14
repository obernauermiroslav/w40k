package com.example.w40k.services;

import com.example.w40k.models.Responses;
import com.example.w40k.models.User;
import com.example.w40k.models.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
   // List<User> findAll();

    User save(User user);

    Optional<User> findById(Long id);
    Optional<User> findByUsername(String user);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    Optional<User> findByUsernameOrEmail(String username, String email);
    ResponseEntity<Responses> register(User user) throws Exception;
    ResponseEntity<Responses> login(UserDto user);
    ResponseEntity<?> activate(String username,String activationCode);

    Optional<User> findByActivationCode(String activationCode);

}
