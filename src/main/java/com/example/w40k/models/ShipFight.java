package com.example.w40k.models;

public class ShipFight {
    private String name; // New field for the ship name
    private int health;
    private int attack;
    private int bonus;
    private String imagePath; // New field for the image path
    private int skillPoints; // New field for skill points

    public ShipFight() {
        // Empty constructor
    }

    public ShipFight(String name, int health, int attack, String imagePath) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.bonus = 0; // Initialize the bonus to 0
        this.imagePath = imagePath; // Set the image path
        this.skillPoints = 2; // Set initial skill points to 2
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void gainPower() {
        attack += 3;
        bonus += 25; // Increase the bonus
        health = 300 + bonus; // Set the health based on the initial value of 100 and the bonus

        // Update the ship's image path based on the current image path
        if (imagePath.equals("/images/Frigate.jpg")) {
            imagePath = "/images/Light-Cruiser.jpg";
            name = "Light Cruiser";
        } else if (imagePath.equals("/images/Light-Cruiser.jpg")) {
            imagePath = "/images/Cruiser.jpg";
            name = "Grand Cruiser";
        } else if (imagePath.equals("/images/Cruiser.jpg")) {
            imagePath = "/images/Battleship.jpeg";
            name = "Battleship";
        } else if (imagePath.equals("/images/Battleship.jpeg")) {
            imagePath = "/images/Gloriana.jpg";
            name = "Gloriana";
        } else if (imagePath.equals("/images/Gloriana.jpg")) {
            imagePath = "/images/Gloriana.jpg";
            name = "Gloriana";
        }
    }


    public int getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public void upgradeHealth() {
        if (skillPoints > 0) {
            health += 30;
            skillPoints -= 1;
        }
    }

    public void upgradeAttack() {
        if (skillPoints > 1) {
            attack += 2;
            skillPoints -= 2;
        }
    }
}




