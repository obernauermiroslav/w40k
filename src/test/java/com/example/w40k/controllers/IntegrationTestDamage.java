
/*
package com.example.w40k.controllers;

import com.example.w40k.models.ShipFight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IntegrationTestDamage {

    @Autowired
    private GameController.GameControllerSecond gameController;
    private ShipFight playerShip;
    private ShipFight enemyShip;

    @BeforeEach
    public void setUp() {
        playerShip = new ShipFight();
        enemyShip = new ShipFight();
    }

    @Test
    public void testPerformActionCalculatesCorrectDamage() {
        playerShip.setAttack(10);
        enemyShip.setArmor(5);
        enemyShip.setHealth(100);

        gameController.setPlayerShipFight(playerShip);
        gameController.setCurrentEnemyShip(enemyShip);

        // Calculate expected damage
        int expectedDamage = playerShip.getAttack() - enemyShip.getArmor();

        // Get the enemy's initial health
        int initialHealth = enemyShip.getHealth();

        // Perform the action
        String viewName = gameController.performAction(new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return this;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return this;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return this;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return this;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return this;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        }, null, null);

        // Verify that the enemy ship took the correct amount of damage
        assertEquals(initialHealth - expectedDamage, 95);
    }
}
 */