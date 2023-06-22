package com.example.w40k.services;

import com.example.w40k.models.Ships;
import com.example.w40k.repositories.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Ships save(Ships ships) {
        return shipRepository.save(ships);
    }


}
