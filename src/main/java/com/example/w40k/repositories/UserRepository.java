package com.example.w40k.repositories;

import com.example.w40k.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByUsernameAndEmailAndPassword(String username, String email, String password);

    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndActivationCode(String username, String activationCode);

    Optional<User> findByActivationCode(String activationCode);
}
