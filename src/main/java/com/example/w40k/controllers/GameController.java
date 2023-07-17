package com.example.w40k.controllers;

import com.example.w40k.models.ShipFight;
import com.example.w40k.models.Ships;
import com.example.w40k.services.ShipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Controller
    public class GameControllerSecond {

        private ShipFight playerShipFight;
        private ShipFight currentEnemyShip;
        private List<ShipFight> enemyShips;
        private boolean gameStarted;

        @GetMapping("/shipGame")
        public String showGame(Model model) {
            if (playerShipFight == null || !gameStarted) {
                initializeGame();
            }

            model.addAttribute("playerShip", playerShipFight);
            model.addAttribute("enemyShip", currentEnemyShip);
            model.addAttribute("gameStarted", gameStarted);
            model.addAttribute("playerAttackMessage", "");
            model.addAttribute("enemyAttackMessage", "");
            model.addAttribute("result", "");

            return "Shipbattle";
        }

        @PostMapping("/startGame")
        public String performAction(Model model, @RequestParam(value = "upgradeHealth", required = false) String upgradeHealth,
                                    @RequestParam(value = "upgradeAttack", required = false) String upgradeAttack) {
            if (playerShipFight != null && !playerShipFight.isDestroyed() && (currentEnemyShip != null || !enemyShips.isEmpty())) {
                int playerAttack = playerShipFight.getAttack();
                int playerArmor = playerShipFight.getArmor();
                int enemyArmor = currentEnemyShip.getArmor();
                int enemyShields = currentEnemyShip.getShield();
                int playerShields = playerShipFight.getShield();


                if (enemyArmor == 0 || enemyShields > 0) {
                    // No armor reduction for enemy
                    playerAttack = playerAttack;
                } else if (enemyArmor == 3) {
                    // Enemy armor reduces player's attack by 3
                    playerAttack -= 3;
                } else if (enemyArmor == 6) {
                    // Enemy armor reduces player's attack by 6
                    playerAttack -= 6;
                }
                else if (enemyArmor == 9) {
                    // Player armor reduces enemy's attack by 6
                    playerAttack -= 9;
                }
                else if (enemyArmor == 12) {
                    // Player armor reduces enemy's attack by 6
                    playerAttack -= 12;
                }
                else if (enemyArmor == 15) {
                    // Player armor reduces enemy's attack by 6
                    playerAttack -= 15;
                }

                if (playerAttack > 0) {
                    currentEnemyShip.takeDamage(playerAttack);
                    if (enemyShields > 0) {
                        model.addAttribute("playerAttackMessage", "Our ship attacks Enemy for " + playerAttack + " damage.");
                    } else {
                    model.addAttribute("playerAttackMessage", "Our ship attacks Enemy for " + playerAttack + " damage." +  "(" + playerShipFight.getAttack() + " - " + enemyArmor + ")"
                    );
                    }
                } else {
                    model.addAttribute("playerAttackMessage", "Our ship attacks Enemy, but the attack is ineffective.");
                }

                if (!currentEnemyShip.isDestroyed()) {
                    int enemyAttack = currentEnemyShip.getAttack();
                    if (playerArmor == 0 || playerShields > 0) {
                        // No armor reduction for player
                        enemyAttack = enemyAttack;
                    } else if (playerArmor == 3) {
                        // Player armor reduces enemy's attack by 3
                        enemyAttack -= 3;
                    } else if (playerArmor == 6) {
                        // Player armor reduces enemy's attack by 6
                        enemyAttack -= 6;
                    }
                    else if (playerArmor == 9) {
                        // Player armor reduces enemy's attack by 6
                        enemyAttack -= 9;
                    }
                    else if (playerArmor == 12) {
                        // Player armor reduces enemy's attack by 6
                        enemyAttack -= 12;
                    }
                    else if (playerArmor == 15) {
                        // Player armor reduces enemy's attack by 6
                        enemyAttack -= 15;
                    }

                    if (enemyAttack > 0) {
                        playerShipFight.takeDamage(enemyAttack);
                        if (playerShields > 0) {
                            model.addAttribute("enemyAttackMessage", "Enemy attacks our ship for " + enemyAttack + " damage.");
                        } else {
                        model.addAttribute("enemyAttackMessage", "Enemy attacks our ship for " + enemyAttack + " damage." +  "(" + currentEnemyShip.getAttack() + " - " + playerArmor + ")"
                        );
                        }
                    } else {
                        model.addAttribute("enemyAttackMessage", "Enemy attacks our ship, but the attack is ineffective.");
                    }
                }

                if (playerShipFight.isDestroyed() && currentEnemyShip.isDestroyed()) {
                    model.addAttribute("result", "It's a tie! Both ships are destroyed.");
                } else if (playerShipFight.isDestroyed()) {
                    model.addAttribute("result", "Our Ship is destroyed. Enemy Ship wins!");
                    // Remove the player ship if it is destroyed
                    playerShipFight = null;
                } else if (currentEnemyShip.isDestroyed()) {
                    playerShipFight.gainPower();
                    model.addAttribute("result", "Enemy Ship is destroyed. Our Ship wins and is upgraded : full repair, +3 attack, +30 max health and stronger shields.");
                    playerShipFight.setSkillPoints(playerShipFight.getSkillPoints() + 2); // Increase skill points by +2

                    // Check if there are more enemy ships
                    if (!enemyShips.isEmpty()) {
                        currentEnemyShip = enemyShips.remove(0); // Take the next enemy ship from the list
                    } else {
                        // All enemy ships are defeated, end the game
                        currentEnemyShip = null;
                    }
                }

                // Handle skill point upgrades
                if (upgradeHealth != null && playerShipFight.getSkillPoints() > 0) {
                    playerShipFight.upgradeHealth();
                    playerShipFight.setSkillPoints(playerShipFight.getSkillPoints() - 1); // Decrease skill points by 1
                }
                if (upgradeAttack != null && playerShipFight.getSkillPoints() > 0) {
                    playerShipFight.upgradeAttack();
                    playerShipFight.setSkillPoints(playerShipFight.getSkillPoints() - 1); // Decrease skill points by 1
                }
            } else {
                if (playerShipFight == null) {
                    model.addAttribute("result", "Our Ship is destroyed. We have lost.");
                } else {
                    model.addAttribute("result", "Congratulations! We have crushed our enemy");
                }
            }

            // Update model attributes with current ship data
            model.addAttribute("playerShip", playerShipFight);
            model.addAttribute("enemyShip", currentEnemyShip);

            return "Shipbattle";
        }
        private void initializeGame() {
            playerShipFight = new ShipFight("Imperial Frigate", 300, 10,  "/images/Frigate.jpg");
            playerShipFight.setShield(120);
            enemyShips = new ArrayList<>();
            enemyShips.add(new ShipFight("Chaos Frigate", 230, 10, "/images/chaos_frigate.jpeg"));
            enemyShips.add(new ShipFight("Chaos Light Cruiser", 280, 12, "/images/chaos_light-cruiser.jpeg"));
            enemyShips.add(new ShipFight("Chaos Grand Cruiser", 330, 15, "/images/chaos_cruiser.jpeg"));
            enemyShips.add(new ShipFight("Chaos Battleship", 420, 18, "/images/chaos_battleship.jpeg"));
            enemyShips.add(new ShipFight("Chaos Gloriana", 530, 22, "/images/chaos_gloriana.jpeg"));
            enemyShips.get(0).setArmor(3);
            enemyShips.get(0).setShield(100);// Chaos Frigate
            enemyShips.get(1).setArmor(6);
            enemyShips.get(1).setShield(150);// Chaos Light Cruiser
            enemyShips.get(2).setArmor(9);
            enemyShips.get(2).setShield(200);// Chaos Grand Cruiser
            enemyShips.get(3).setArmor(12);
            enemyShips.get(3).setShield(250);// Chaos Battleship
            enemyShips.get(4).setArmor(15);
            enemyShips.get(4).setShield(300);// Chaos Gloriana
            currentEnemyShip = enemyShips.remove(0);
            gameStarted = true;
        }

        private int getRandomAttack() {
            // Generate a random number between 5 and 10 (inclusive)
            return (int) (Math.random() * 6) + 5;
        }

        @PostMapping("/upgradeHealth")
        public String upgradeHealth(Model model) {
            if (playerShipFight != null && playerShipFight.getSkillPoints() > 0) {
                playerShipFight.upgradeHealth();
            }
            return "redirect:/shipGame";
        }

        @PostMapping("/upgradeAttack")
        public String upgradeAttack(Model model) {
            if (playerShipFight != null && playerShipFight.getSkillPoints() > 0) {
                playerShipFight.upgradeAttack();
            }
            return "redirect:/shipGame";
        }

        @PostMapping("/upgradeArmor")
        public String upgradeArmor(Model model) {
            if (playerShipFight != null && playerShipFight.getSkillPoints() > 0) {
                playerShipFight.upgradeArmor();
            }
            return "redirect:/shipGame";
        }
        @PostMapping("/upgradeShield")
        public String upgradeShield(Model model) {
            if (playerShipFight != null && playerShipFight.getSkillPoints() > 0) {
                playerShipFight.upgradeShield();
            }
            return "redirect:/shipGame";
        }

    }
}

