package com.example.w40k.repositories;

import com.example.w40k.models.ShipClass;
import com.example.w40k.models.Ships;
import com.example.w40k.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipRepository extends JpaRepository<Ships, Long> {
    List<Ships> findByTitleContainingIgnoreCase(String title);
    List<Ships> findByTitleContainingIgnoreCaseAndType(String title, ShipClass type);
    List<Ships> findAllByType(ShipClass type);
    List<Ships> findByUserIn(List<UserRole> roles);
    List<Ships> findChaosByUser(UserRole role);
}
