package com.example.w40k.services;

import com.example.w40k.models.Ships;

import java.util.List;

public interface ShipService {
List<Ships> findAll();

    Ships save(Ships ships);
}
