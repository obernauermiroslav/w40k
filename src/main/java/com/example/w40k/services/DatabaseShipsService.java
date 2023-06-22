package com.example.w40k.services;

import com.example.w40k.models.Ships;
import com.example.w40k.repositories.ShipRepository;
import org.springframework.stereotype.Service;

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
        return shipRepository.findAll();
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
