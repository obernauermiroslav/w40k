package com.example.w40k.services;

import com.example.w40k.models.Ships;
import com.example.w40k.models.UserRole;
import com.example.w40k.repositories.ShipRepository;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DatabaseShipsService implements ShipService {

    public DatabaseShipsService(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    private final ShipRepository shipRepository;

    @Override
    public List<Ships> findAll() {
        List<UserRole> allowedRoles = Arrays.asList(UserRole.IMPERIAL_NAVY, UserRole.SPACE_MARINES);
        return shipRepository.findByUserIn(allowedRoles);
    }

    @Override
    public List<Ships> findChaos() {
        return shipRepository.findChaosByUser(UserRole.CHAOS_FORCES);
    }

    @Override
    public Optional<Ships> findById(Long id) {
        return shipRepository.findById(id);
    }

    @Override
    public Ships save(Ships ships) {
        return shipRepository.save(ships);
    }

    @Override
    public void delete(Ships ships) {
        shipRepository.delete(ships);
    }
}