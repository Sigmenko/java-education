package battles;

import droids.Droid;
import java.util.List;
import java.util.Scanner;
import core.GameManager;
import core.ConsoleColors;

public class Fight1v1Manager {

    /**
     * Запускає бій 1 на 1 між двома обраними дроїдами.
     */
    public static void startFight(List<Droid> allDroids, Scanner scanner) {
        if (allDroids.size() < 2) {
            System.out.println("⛔ Потрібно мінімум 2 дроїди для бою 1 на 1.");
            return;
        }

        GameManager.showDroidList();

        System.out.print("Оберіть номер Дроїда 1: ");
        int index1 = getDroidIndex(scanner, allDroids);
        if (index1 == -1) return;
        Droid droid1 = allDroids.get(index1);

        System.out.print("Оберіть номер Дроїда 2: ");
        int index2 = getDroidIndex(scanner, allDroids, index1);
        if (index2 == -1) return;
        Droid droid2 = allDroids.get(index2);

        // Встановлюємо повне здоров'я перед боєм
        droid1.setHealth(droid1.getMaxHealth());
        droid2.setHealth(droid2.getMaxHealth());
        droid1.setEnergy(droid1.getMaxEnergy());
        droid2.setEnergy(droid2.getMaxEnergy());

        System.out.println(ConsoleColors.BOLD + ConsoleColors.PURPLE +
                "\n*** БІЙ 1 НА 1: " + droid1.getName() + " vs " + droid2.getName() + " ***" + ConsoleColors.RESET);

        int round = 1;
        while (droid1.isAlive() && droid2.isAlive()) {
            System.out.println("\n===== РАУНД " + round + " =====");

            // Хід Дроїда 1
            if (droid1.isAlive()) {
                System.out.println("--- Хід " + droid1.getName() + " ---");
                droid1.passiveRecharge();
                droid1.attack(droid2);
                System.out.println("   " + droid2.getStatusInfo());
            }
            if (!droid2.isAlive()) break;

            // Хід Дроїда 2
            if (droid2.isAlive()) {
                System.out.println("--- Хід " + droid2.getName() + " ---");
                droid2.passiveRecharge();
                droid2.attack(droid1);
                System.out.println("   " + droid1.getStatusInfo());
            }

            round++;
        }

        System.out.println(ConsoleColors.BOLD + "\n*** БІЙ ЗАВЕРШЕНО! ***" + ConsoleColors.RESET);
        if (droid1.isAlive()) {
            System.out.println(ConsoleColors.BOLD + ConsoleColors.GREEN + "🏆 Переміг " + droid1.getName() + "!" + ConsoleColors.RESET);
        } else if (droid2.isAlive()) {
            System.out.println(ConsoleColors.BOLD + ConsoleColors.GREEN + "🏆 Переміг " + droid2.getName() + "!" + ConsoleColors.RESET);
        } else {
            System.out.println("Нічия! Обидва дроїди знищені.");
        }

        // Відновлюємо здоров'я після бою
        droid1.setHealth(droid1.getMaxHealth());
        droid2.setHealth(droid2.getMaxHealth());
    }

    private static int getDroidIndex(Scanner scanner, List<Droid> droids) {
        return getDroidIndex(scanner, droids, -1);
    }

    private static int getDroidIndex(Scanner scanner, List<Droid> droids, int excludeIndex) {
        if (!scanner.hasNextInt()) {
            System.out.println("⚠️ Некоректний ввід. Потрібен номер.");
            scanner.nextLine();
            return -1;
        }
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        if (index < 0 || index >= droids.size()) {
            System.out.println("⚠️ Некоректний номер дроїда.");
            return -1;
        }
        if (index == excludeIndex) {
            System.out.println("⚠️ Цей дроїд уже обраний. Оберіть іншого.");
            return -1;
        }
        return index;
    }
}