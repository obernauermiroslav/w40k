package com.example.w40k.repositories;

import com.example.w40k.models.Ships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends JpaRepository<Ships, Long> {
}
