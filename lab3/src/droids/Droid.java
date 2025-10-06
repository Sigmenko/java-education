package droids;

import java.io.Serializable;
import java.lang.Math;
import core.ConsoleColors;

public abstract class Droid implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private int health;
    private final int maxHealth;
    private final int damage;

    // ✅ ВИПРАВЛЕНО: Поле тепер коректно ініціалізовано
    private final double armorModifier = 0.1;

    private int energy;
    private final int maxEnergy = 100;
    private final int passiveEnergyGain = 10;

    public Droid(String name, int health, int damage) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.damage = damage;
        this.energy = maxEnergy;
    }

    public void attack(Droid defender) {
        System.out.println(this.getName() + " атакує " + defender.getName() + ".");
        defender.takeDamage(this.damage);
    }

    public void takeDamage(int damage) {
        // ✅ ВИПРАВЛЕНО: Правильне приведення до int після обчислення
        double effectiveDamageDouble = damage * (1.0 - this.armorModifier);
        int effectiveDamage = (int) Math.round(effectiveDamageDouble);

        if (effectiveDamage < 0) effectiveDamage = 0;

        this.health -= effectiveDamage;
        if (this.health < 0) this.health = 0;

        System.out.println(ConsoleColors.RED + "💔 " + this.name + " отримує " + effectiveDamage + " шкоди." + ConsoleColors.RESET);
    }

    // --- МЕТОДИ ЕНЕРГІЇ ---
    public void passiveRecharge() {
        this.energy = Math.min(maxEnergy, this.energy + passiveEnergyGain);
    }

    public boolean consumeEnergy(int cost) {
        if (this.energy >= cost) {
            this.energy -= cost;
            return true;
        }
        return false;
    }

    public boolean isAlive() { return health > 0; }

    @Override
    public String toString() {
        return name + " (" + this.getClass().getSimpleName() + ")";
    }

    // --- МЕТОДИ ВІЗУАЛІЗАЦІЇ ---
    // Публічний метод для виклику ззовні
    public String getStatusInfo() {
        return ConsoleColors.BOLD + this.toString() + ConsoleColors.RESET +
                " | HP: " + getHealthBar() + " | EN: " + getEnergyBar();
    }

    // Приватні методи, які мають викликатися лише з getStatusInfo()
    private String getHealthBar() {
        int percent = (int) Math.round((double) health * 100 / maxHealth);
        String color = (percent > 50) ? ConsoleColors.GREEN : (percent > 20) ? ConsoleColors.YELLOW : ConsoleColors.RED;
        return ConsoleColors.getBar(health, maxHealth, 10, color, "█") +
                " " + percent + "% (" + health + "/" + maxHealth + ")" + ConsoleColors.RESET;
    }

    private String getEnergyBar() {
        int percent = (int) Math.round((double) energy * 100 / maxEnergy);
        String color = ConsoleColors.BLUE;
        return ConsoleColors.getBar(energy, maxEnergy, 10, color, "▓") +
                " " + percent + "%";
    }

    // --- ГЕТТЕРИ/СЕТТЕРИ ---
    public String getName() { return name; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getMaxHealth() { return maxHealth; }
    public int getDamage() { return damage; }
    public int getEnergy() { return energy; }
    public int getMaxEnergy() { return maxEnergy; }
    public int getPassiveEnergyGain() { return passiveEnergyGain; }
    // ✅ ВИПРАВЛЕНО: setEnergy додано для відновлення після бою
    public void setEnergy(int energy) { this.energy = energy; }
}