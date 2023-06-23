package com.example.w40k.controllers;

import com.example.w40k.models.Ships;
import com.example.w40k.services.ShipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GameController {

    private final ShipService shipService;

    public GameController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping("/game")
    public String game(Model model) {
        return "game";
    }

    @PostMapping("/game")
    public String playGame(@RequestParam("shipId") Long shipId, Model model) {
        List<Ships> ships = shipService.findAll();
        Ships playerShip = findShipById(shipId, ships);
        Ships computerShip = findShipById(shipId, ships); // Assuming computer's shipId is always 2

        int userDiceRoll = rollDice();
        int computerDiceRoll = rollDice();

        String result;
        if (userDiceRoll > computerDiceRoll) {
            result = "Enemy ship is destroyed!";
        } else if (userDiceRoll < computerDiceRoll) {
            result = "Our ship is destroyed!";
        } else {
            result = "Our ship is damaged, we need to repair our ship and attack again ";
        }

        model.addAttribute("result", result);
        model.addAttribute("playerShip", playerShip);
        model.addAttribute("computerShip", computerShip);
        model.addAttribute("userDiceRoll", userDiceRoll);
        model.addAttribute("computerDiceRoll", computerDiceRoll);
        return "game";
    }

    private int rollDice() {
        return (int) (Math.random() * 6) + 1;
    }

    private Ships findShipById(Long shipId, List<Ships> ships) {
        for (Ships ship : ships) {
            if (ship.getId().equals(shipId)) {
                return ship;
            }
        }
        return null;
    }
}

