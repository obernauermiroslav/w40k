package com.example.w40k.services;

import com.example.w40k.models.Ships;
import java.util.List;
import java.util.Optional;

public interface ShipService {
List<Ships> findAll();

List<Ships> findChaos();

    Ships save(Ships ships);

     Optional<Ships> findById(Long id);

    void delete(Ships ships);
}
