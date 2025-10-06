package core;

import droids.Droid;
import droids.TankDroid;
import droids.AttackerDroid;
import droids.HealerDroid;

import battles.Fight1v1Manager;
import battles.FightTeamVsTeamManager;
import battles.BattleHistoryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    private static final List<Droid> droidList = new ArrayList<>();
    public static final Scanner scanner = new Scanner(System.in);

    private static List<Droid> lastTeam1 = new ArrayList<>();
    private static List<Droid> lastTeam2 = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Введено некоректне число.");
                scanner.nextLine();
                choice = 0;
                continue;
            }
            executeChoice(choice);
        } while (choice != 7);
    }

    private static void showMenu() {
        System.out.println("\n--- МЕНЮ ГРИ БИТВА ДРОЇДІВ ---");
        System.out.println("1. Створити дроїда (обраного виду)");
        System.out.println("2. Показати список створених дроїдів");
        System.out.println("3. Запустити бій 1 на 1");
        System.out.println("4. Запустити бій команда на команду");
        System.out.println("5. Зберегти останній командний бій у файл"); // ✅ Реалізовано
        System.out.println("6. Відтворити бій зі збереженого файлу");    // ✅ Реалізовано
        System.out.println("7. Вийти з програми");
        System.out.print("Оберіть команду: ");
    }

    private static void executeChoice(int choice) {
        switch (choice) {
            case 1:
                createDroid();
                break;
            case 2:
                showDroidList();
                break;
            case 3:
                // ✅ РОЗКОМЕНТОВАНО: Тепер викликаємо реалізований Fight1v1Manager
                Fight1v1Manager.startFight(droidList, scanner);
                break;
            case 4:
                // ... (Логіка вибору команд) ...
                List<Droid> currentTeam1 = FightTeamVsTeamManager.selectTeam(droidList, scanner, 1);
                if (currentTeam1 == null) break;
                List<Droid> currentTeam2 = FightTeamVsTeamManager.selectTeam(droidList, scanner, 2, currentTeam1);
                if (currentTeam2 == null) break;

                // Зберігаємо посилання на ці команди для опції 5
                lastTeam1 = currentTeam1;
                lastTeam2 = currentTeam2;

                FightTeamVsTeamManager.startFight(currentTeam1, currentTeam2);
                break;

            case 5: // ЗБЕРЕЖЕННЯ
                if (lastTeam1.isEmpty() && lastTeam2.isEmpty()) {
                    System.out.println("⚠️ Спочатку потрібно провести командний бій (Команда 4), щоб мати що зберігати.");
                } else {
                    // ✅ РОЗКОМЕНТОВАНО: Викликаємо функцію збереження
                    BattleHistoryManager.saveBattle(lastTeam1, lastTeam2);
                }
                break;

            case 6: // ВІДТВОРЕННЯ
                // ✅ РОЗКОМЕНТОВАНО: Викликаємо функцію відтворення
                BattleHistoryManager.loadAndReplayBattle();
                break;

            case 7: // ВИХІД
                System.out.println("Програма завершує роботу. Бувай!");
                break;

            default:
                System.out.println("⚠️ Невідома команда. Спробуйте число від 1 до 7.");
        }
    }

    public static void showDroidList() {
        System.out.println(ConsoleColors.CYAN + "\n--- СПИСОК СТВОРЕНИХ ДРОЇДІВ (" + droidList.size() + ") ---" + ConsoleColors.RESET);
        if (droidList.isEmpty()) {
            System.out.println("Список порожній. Створіть дроїдів (Команда 1).");
            return;
        }

        for (int i = 0; i < droidList.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + droidList.get(i).getStatusInfo());
        }
    }

    // ... (createDroid - без змін) ...
    private static void createDroid() {
        System.out.println("\n--- СТВОРЕННЯ ДРОЇДА ---");
        System.out.println("Оберіть тип дроїда:");
        System.out.println("  1. " + ConsoleColors.CYAN + "TankDroid (🛡️ Броня)" + ConsoleColors.RESET);
        System.out.println("  2. " + ConsoleColors.RED + "AttackerDroid (💥 Крит)" + ConsoleColors.RESET);
        System.out.println("  3. " + ConsoleColors.GREEN + "HealerDroid (💚 Лікування)" + ConsoleColors.RESET);
        System.out.print("Ваш вибір: ");

        int typeChoice = -1;
        if (scanner.hasNextInt()) {
            typeChoice = scanner.nextInt();
        }
        scanner.nextLine();

        System.out.print("Введіть ім'я для дроїда: ");
        String name = scanner.nextLine();

        Droid newDroid = null;

        switch (typeChoice) {
            case 1:
                newDroid = new TankDroid(name);
                break;
            case 2:
                newDroid = new AttackerDroid(name);
                break;
            case 3:
                newDroid = new HealerDroid(name);
                break;
            default:
                System.out.println("⚠️ Некоректний тип дроїда.");
                return;
        }

        droidList.add(newDroid);
        System.out.println(ConsoleColors.BOLD + ConsoleColors.GREEN + "✅ Дроїд " + newDroid.getName() + " успішно створений!" + ConsoleColors.RESET);
    }
}