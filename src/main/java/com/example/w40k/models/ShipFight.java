package com.example.w40k.models;

public class ShipFight {
    private String name;
    private int health;
    private int attack;
    private int bonus;
    private int armor;
    private int shield;
    private String imagePath;
    private int skillPoints;
    private int damageReduction;

    public ShipFight() {
        // Empty constructor
    }

    public ShipFight(String name, int health, int attack, String imagePath) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.bonus = 0;
        this.armor = 0;
        this.shield = 0;
        this.imagePath = imagePath;
        this.skillPoints = 0;
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

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getArmor() {
        return armor;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getShield() {
        return shield;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setDamageReduction(int damageReduction) {
        this.damageReduction = damageReduction;
    }

    public int getDamageReduction() {
        return damageReduction;
    }

    public void takeDamage(int damage) {
        int totalDamage = damage;
        if (shield > 0) {
            if (totalDamage > shield) {
                totalDamage -= shield;
                shield = 0;
            } else {
                shield -= totalDamage;
                totalDamage = 0;
            }
        }
        if (totalDamage > 0) {
            health -= totalDamage;
        }
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void gainPower() {
        attack += 3;
        bonus += 30;
        health = 300 + bonus;
        shield += 200 + bonus;

        // Update the ship's image path based on the current image path
        if (imagePath.equals("/images/Frigate.jpg")) {
            imagePath = "/images/Light-Cruiser.jpg";
            name = "Imperial Light Cruiser";
        } else if (imagePath.equals("/images/Light-Cruiser.jpg")) {
            imagePath = "/images/battlecruiser.jpg";
            name = "Imperial Battlecruiser";
        } else if (imagePath.equals("/images/battlecruiser.jpg")) {
            imagePath = "/images/Cruiser.jpg";
            name = "Imperial Grand Cruiser";
        } else if (imagePath.equals("/images/Cruiser.jpg")) {
            imagePath = "/images/Battleship.jpeg";
            name = "Imperial Battleship";
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
            health += 40;
            skillPoints -= 1;
        }
    }

    public void upgradeAttack() {
        if (skillPoints > 1) {
            attack += 2;
            skillPoints -= 2;
        }
    }

    public void upgradeArmor() {
        if (skillPoints > 2) {
            armor += 3;
            skillPoints -= 3;
        }
    }

    public void upgradeShield() {
        if (skillPoints > 2) {
            shield += 100;
            skillPoints -= 3;
        }
    }
    public void defend() {
        int damageReduction = getAttack() ;
        setDamageReduction(damageReduction);
    }
    public int calculateDamage(int damage) {
        int damageAfterReduction = damage - getDamageReduction();
        if (damageAfterReduction < 0) {
            damageAfterReduction = 0; // Ensure damage cannot be negative
        }
        return damageAfterReduction;
    }
}