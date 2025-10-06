package droids;

import core.ConsoleColors;

public class HealerDroid extends Droid {
    private static final int HEAL_BONUS = 30;
    private static final int ENERGY_COST = 30;

    public HealerDroid(String name) {
        super(name, 90, 5);
    }

    // ✅ ДОДАНО: Метод для лікування союзника
    public boolean healAlly(Droid target) {
        if (consumeEnergy(ENERGY_COST)) {
            int healAmount = HEAL_BONUS;
            target.setHealth(Math.min(target.getMaxHealth(), target.getHealth() + healAmount));
            System.out.println(ConsoleColors.BOLD + ConsoleColors.GREEN +
                    "💚 ПОСИЛЕНЕ ЛІКУВАННЯ! " + this.getName() + " відновлює " + healAmount + " HP для " + target.getName() + ConsoleColors.RESET);
            return true;
        } else {
            System.out.println(ConsoleColors.YELLOW + this.getName() +
                    " не вистачило енергії для посиленого лікування. " + ConsoleColors.RESET);
            return false;
        }
    }

    // ✅ ДОДАНО: Метод для самолікування
    public boolean healSelf() {
        if (consumeEnergy(ENERGY_COST / 2)) { // Самолікування коштує менше
            int healAmount = HEAL_BONUS / 2;
            this.setHealth(Math.min(this.getMaxHealth(), this.getHealth() + healAmount));
            System.out.println(ConsoleColors.BOLD + ConsoleColors.GREEN +
                    "💚 САМОЛІКУВАННЯ! " + this.getName() + " відновлює " + healAmount + " HP." + ConsoleColors.RESET);
            return true;
        } else {
            System.out.println(ConsoleColors.YELLOW + this.getName() +
                    " не вистачило енергії для самолікування. " + ConsoleColors.RESET);
            return false;
        }
    }

    @Override
    public void attack(Droid defender) {
        System.out.println(this.getName() + " завдає мінімальної шкоди.");
        super.attack(defender);
    }
}