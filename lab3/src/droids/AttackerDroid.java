package droids;

import core.ConsoleColors;

public class AttackerDroid extends Droid {
    private static final int BONUS_DAMAGE = 20;
    private static final int ENERGY_COST = 20;

    public AttackerDroid(String name) {
        super(name, 100, 25);
    }

    @Override
    public void attack(Droid defender) {
        // ✅ ВИПРАВЛЕНО: Math.random() < 0.3 - порівняння коректне
        if (Math.random() < 0.3) {
            if (consumeEnergy(ENERGY_COST)) {
                System.out.println(ConsoleColors.BOLD + ConsoleColors.RED +
                        "🔥 СПЕЦІАЛЬНА АТАКА! " + this.getName() + " використовує " + ENERGY_COST + " EN." + ConsoleColors.RESET);
                // ✅ ВИПРАВЛЕНО: Передача int
                defender.takeDamage(this.getDamage() + BONUS_DAMAGE);
            } else {
                System.out.println(ConsoleColors.YELLOW + this.getName() +
                        " спробував СПЕЦІАЛЬНУ АТАКУ, але не вистачило енергії. (Звичайна атака)" + ConsoleColors.RESET);
                super.attack(defender);
            }
        } else {
            super.attack(defender);
        }
    }
}