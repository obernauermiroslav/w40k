package com.example.w40k.controllers;

import com.example.w40k.models.ShipFight;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestDamage
{

    @Test
    public void testDamageCalculation() {
        // Create a player ship with attack = 25
        ShipFight playerShip = new ShipFight();
        playerShip.setAttack(25);

        // Create an enemy ship with armor = 12 and health = 150
        ShipFight enemyShip = new ShipFight();
        enemyShip.setArmor(12);
        enemyShip.setHealth(150);

        // Calculate the damage
        int damage = playerShip.getAttack() - enemyShip.getArmor();

        // Update the enemy ship's health based on the damage
        int expectedHealth = enemyShip.getHealth() - damage;
        enemyShip.setHealth(expectedHealth);

        // Verify that the damage calculation is correct
        assertEquals(13, damage);
        assertEquals(137, enemyShip.getHealth());
    }
}