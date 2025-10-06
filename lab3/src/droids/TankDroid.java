package droids;

import core.ConsoleColors;

public class TankDroid extends Droid {
    private static final double DEFENSE_BONUS = 0.5;
    private static final int ENERGY_COST = 25;
    private boolean defenseStanceActive = false;

    // ✅ ВИПРАВЛЕНО: Конструктор викликає базовий конструктор з int, int, int
    public TankDroid(String name) {
        super(name, 150, 10);
    }

    public boolean activateDefenseStance() {
        if (consumeEnergy(ENERGY_COST)) {
            this.defenseStanceActive = true;
            System.out.println(ConsoleColors.CYAN + this.getName() +
                    " активує ОБОРОННУ СТІЙКУ! Захист +50% на цей хід." + ConsoleColors.RESET);
            return true;
        } else {
            System.out.println(ConsoleColors.YELLOW + this.getName() +
                    " не зміг активувати СТІЙКУ: недостатньо енергії." + ConsoleColors.RESET);
            return false;
        }
    }

    @Override
    public void takeDamage(int damage) {
        if (defenseStanceActive) {
            // ✅ ВИПРАВЛЕНО: Обчислення нової шкоди
            damage = (int) Math.round(damage * (1.0 - DEFENSE_BONUS));
            this.defenseStanceActive = false;
        }
        super.takeDamage(damage);
    }
}