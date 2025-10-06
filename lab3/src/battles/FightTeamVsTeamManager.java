package battles;

import droids.Droid;
import droids.HealerDroid; // ✅ ВИПРАВЛЕНО: Відсутній символ HealerDroid тепер імпортується
import droids.TankDroid;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import core.GameManager;
import core.ConsoleColors;

public class FightTeamVsTeamManager {

    public static void startFight(List<Droid> team1, List<Droid> team2) {
        if (team1 == null || team2 == null || team1.isEmpty() || team2.isEmpty()) {
            System.out.println("⛔ Бій не може розпочатися: команди не сформовані або порожні.");
            return;
        }

        System.out.println(ConsoleColors.BOLD + ConsoleColors.PURPLE + "\n*** БІЙ РОЗПОЧАТО: Команда 1 (" + team1.size() + ") vs Команда 2 (" + team2.size() + ") ***" + ConsoleColors.RESET);

        int round = 1;

        while (!team1.isEmpty() && !team2.isEmpty()) {
            System.out.println("\n===== РАУНД " + round + " =====");

            System.out.println("--- ХІД КОМАНДИ 1 ---");
            performTeamTurn(team1, team2);
            if (team2.isEmpty()) break;

            System.out.println("--- ХІД КОМАНДИ 2 ---");
            performTeamTurn(team2, team1);

            round++;
        }

        System.out.println(ConsoleColors.BOLD + "\n*** БІЙ ЗАВЕРШЕНО! ***" + ConsoleColors.RESET);
        if (team1.isEmpty()) {
            System.out.println(ConsoleColors.BOLD + ConsoleColors.GREEN + "🏆 Перемогла КОМАНДА 2! Залишилося дроїдів: " + team2.size() + ConsoleColors.RESET);
        } else if (team2.isEmpty()) {
            System.out.println(ConsoleColors.BOLD + ConsoleColors.GREEN + "🏆 Перемогла КОМАНДА 1! Залишилося дроїдів: " + team1.size() + ConsoleColors.RESET);
        } else {
            System.out.println("Нічия! Обидві команди знищено.");
        }

        restoreTeamHealth(team1);
        restoreTeamHealth(team2);
        System.out.println("Здоров'я усіх дроїдів відновлено.");
    }

    public static List<Droid> selectTeam(List<Droid> allDroids, Scanner scanner, int teamNumber) {
        return selectTeam(allDroids, scanner, teamNumber, new ArrayList<>());
    }

    public static List<Droid> selectTeam(List<Droid> allDroids, Scanner scanner, int teamNumber, List<Droid> excludedDroids) {
        List<Droid> team = new ArrayList<>();
        System.out.println("\n--- ФОРМУВАННЯ КОМАНДИ " + teamNumber + " ---");
        System.out.print("Введіть розмір команди (мін. 2): ");

        int teamSize;
        if (scanner.hasNextInt()) {
            teamSize = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("⚠️ Некоректний ввід.");
            scanner.nextLine();
            return null;
        }

        if (teamSize < 2 || teamSize > (allDroids.size() - excludedDroids.size())) {
            System.out.println("⚠️ Некоректний розмір команди або недостатньо вільних дроїдів.");
            return null;
        }

        System.out.println("Оберіть " + teamSize + " дроїдів (за номером) для Команди " + teamNumber + ":");

        for (int i = 0; i < teamSize; i++) {
            GameManager.showDroidList();
            System.out.print("Вибір " + (i + 1) + "/" + teamSize + ": Введіть номер дроїда: ");

            if (scanner.hasNextInt()) {
                // ✅ ВИПРАВЛЕНО: Індекс отримується як int
                int index = scanner.nextInt() - 1;
                scanner.nextLine();

                if (index >= 0 && index < allDroids.size()) {
                    Droid selectedDroid = allDroids.get(index);

                    if (team.contains(selectedDroid)) {
                        System.out.println("⚠️ Цей дроїд уже в команді. Оберіть іншого.");
                        i--;
                    } else if (excludedDroids.contains(selectedDroid)) {
                        System.out.println("⛔ Цей дроїд уже в іншій команді. Оберіть іншого.");
                        i--;
                    } else {
                        team.add(selectedDroid);
                        System.out.println("✅ " + selectedDroid.getName() + " додано до Команди " + teamNumber + ".");
                    }
                } else {
                    System.out.println("⚠️ Некоректний номер дроїда.");
                    i--;
                }
            } else {
                System.out.println("⚠️ Некоректний ввід. Потрібен номер.");
                scanner.nextLine();
                i--;
            }
        }
        return team;
    }

    private static void performTeamTurn(List<Droid> attackingTeam, List<Droid> defendingTeam) {
        List<Droid> aliveAttackers = new ArrayList<>(attackingTeam);

        for (Droid attacker : aliveAttackers) {
            if (!attacker.isAlive()) continue;

            // 1. ПАСИВНА РЕГЕНЕРАЦІЯ ЕНЕРГІЇ
            attacker.passiveRecharge();
            // ✅ ВИПРАВЛЕНО: Використовуємо getStatusInfo(), а не приватний getEnergyBar()
            System.out.println(ConsoleColors.PURPLE + "   [EN] " + attacker.getName() +
                    " отримує " + attacker.getPassiveEnergyGain() + " EN. (Поточний EN: " + attacker.getEnergy() + ")" + ConsoleColors.RESET);

            // 2. ПРІОРИТЕТ ТАНКА (ВИКОРИСТАННЯ ЗДІБНОСТІ)
            if (attacker instanceof TankDroid tank) {
                if (tank.getHealth() < tank.getMaxHealth()) {
                    if (tank.activateDefenseStance()) {
                        continue;
                    }
                }
            }

            // 3. ЗВИЧАЙНА ЛОГІКА ДІЙ
            if (attacker instanceof HealerDroid healer) {
                Droid weakestAlly = findWeakestAlly(attackingTeam);

                if (weakestAlly != null && weakestAlly.getHealth() < weakestAlly.getMaxHealth()) {
                    // ✅ ВИПРАВЛЕНО: Виклик healAlly з об'єкта healer (усуває Cannot resolve method)
                    healer.healAlly(weakestAlly);
                } else {
                    attackTarget(attacker, defendingTeam);
                }
            } else {
                attackTarget(attacker, defendingTeam);
            }
            if (defendingTeam.isEmpty()) break;
        }
    }

    private static void attackTarget(Droid attacker, List<Droid> defendingTeam) {
        Droid target = findTarget(defendingTeam);
        if (target != null) {
            attacker.attack(target);

            System.out.println("   " + target.getStatusInfo());

            if (!target.isAlive()) {
                defendingTeam.remove(target);
                System.out.println(ConsoleColors.BOLD + ConsoleColors.RED + "💥 Дроїд " + target.getName() + " ЗНИЩЕНИЙ!" + ConsoleColors.RESET);
            }
        }
    }

    private static Droid findWeakestAlly(List<Droid> team) {
        if (team.isEmpty()) return null;

        Droid weakest = null;
        double minHealthRatio = 1.0;

        for (Droid droid : team) {
            // ✅ ВИПРАВЛЕНО: Явне приведення до double, щоб уникнути несумісності типів при діленні
            double currentRatio = (double) droid.getHealth() / droid.getMaxHealth();
            if (currentRatio < minHealthRatio) {
                minHealthRatio = currentRatio;
                weakest = droid;
            }
        }
        return weakest;
    }

    private static Droid findTarget(List<Droid> targetTeam) {
        if (targetTeam.isEmpty()) return null;

        Droid target = null;
        int minHealth = Integer.MAX_VALUE;

        for (Droid droid : targetTeam) {
            if (droid.isAlive() && droid.getHealth() < minHealth) {
                minHealth = droid.getHealth();
                target = droid;
            }
        }
        return target;
    }

    private static void restoreTeamHealth(List<Droid> team) {
        for (Droid droid : team) {
            droid.setHealth(droid.getMaxHealth());
            // ✅ ВИПРАВЛЕНО: Використання setEnergy
            droid.setEnergy(droid.getMaxEnergy());
        }
    }
}