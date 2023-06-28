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
        List<Ships> ships = shipService.findAll();
        List<Ships> chaosShips = shipService.findChaos();
        model.addAttribute("ships", ships);
        model.addAttribute("chaosShips", chaosShips);
        return "game";
    }

    @PostMapping("/game")
    public String playGame(@RequestParam("shipId") Long shipId, Model model) {
        List<Ships> ships = shipService.findAll();
        List<Ships> chaosShips = shipService.findChaos();
        Ships playerShip = findShipById(shipId, ships);
        Ships computerShip = null;

        int userDiceRoll = rollDice();
        int computerDiceRoll = 0;

        String result;

        if (playerShip != null && !chaosShips.isEmpty()) {
            computerShip = chooseRandomShip(chaosShips);
            computerDiceRoll = rollDice();

            if (userDiceRoll > computerDiceRoll) {
                result = "Enemy ship is ";

                if (computerShip.isDamaged()) {
                    result += "destroyed!";
                    shipService.delete(computerShip);
                    chaosShips.remove(computerShip);
                } else {
                    result += "damaged!";
                    computerShip.setDamaged(true);
                    computerShip.setTitle(computerShip.getTitle() + " (damaged)");
                    shipService.save(computerShip);
                }

                if (chaosShips.isEmpty()) {
                    result += " We have won.";
                }
            } else if (userDiceRoll < computerDiceRoll) {
                result = "Our ship is ";

                if (playerShip.isDamaged()) {
                    result += "destroyed!";
                    shipService.delete(playerShip);
                    ships.remove(playerShip);
                } else {
                    result += "damaged!";
                    playerShip.setDamaged(true);
                    playerShip.setTitle(playerShip.getTitle() + " (damaged)");
                    shipService.save(playerShip);
                }

                if (ships.isEmpty()) {
                    result += " We have lost.";
                }
            } else {
                result = "We have suffered heavy losses, but so does our enemy";

                if (playerShip.isDamaged()) {
                    result += " Our ship is destroyed!";
                    shipService.delete(playerShip);
                    ships.remove(playerShip);

                    if (ships.isEmpty() && !chaosShips.isEmpty()) {
                        result += " We have lost.";
                    }
                } else {
                    result += " Our ship is damaged!";
                    playerShip.setDamaged(true);
                    playerShip.setTitle(playerShip.getTitle() + " (damaged)");
                    shipService.save(playerShip);
                }

                if (computerShip.isDamaged()) {
                    result += " Enemy ship is destroyed!";
                    shipService.delete(computerShip);
                    chaosShips.remove(computerShip);

                    if (chaosShips.isEmpty() && ships.isEmpty()) {
                        result += " No more ships to fight.";
                    } else if (chaosShips.isEmpty()) {
                        result += " We have won.";
                    }
                } else {
                    result += " Enemy ship is damaged!";
                    computerShip.setDamaged(true);
                    computerShip.setTitle(computerShip.getTitle() + " (damaged)");
                    shipService.save(computerShip);
                }
            }
        } else if (chaosShips.isEmpty() && ships.isEmpty()) {
            result = "No more ships to fight.";
        } else if (chaosShips.isEmpty()) {
            result = "Enemy ship is destroyed! We have won.";
        } else {
            result = "Invalid ship or ship list is empty.";
        }

        chaosShips = shipService.findChaos();

        model.addAttribute("result", result);
        model.addAttribute("playerShip", playerShip);
        model.addAttribute("computerShip", computerShip);
        model.addAttribute("userDiceRoll", userDiceRoll);
        model.addAttribute("computerDiceRoll", computerDiceRoll);
        model.addAttribute("ships", ships);
        model.addAttribute("chaosShips", chaosShips);

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

    private Ships chooseRandomShip(List<Ships> ships) {
        int numShips = ships.size();
        if (numShips == 1) {
            return ships.get(0);
        } else if (numShips > 1) {
            int randomIndex = (int) (Math.random() * numShips);
            return ships.get(randomIndex);
        }
        return null;
    }
}