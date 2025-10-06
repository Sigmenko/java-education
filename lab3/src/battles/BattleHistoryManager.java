package battles;

import droids.Droid;
import java.io.*;
import java.util.List;

public class BattleHistoryManager {

    private static final String FILENAME = "battle_history.ser";

    /**
     * Зберігає стан двох команд у файл.
     */
    public static void saveBattle(List<Droid> team1, List<Droid> team2) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(team1);
            oos.writeObject(team2);
            System.out.println("✅ Бій успішно збережено у " + FILENAME);
        } catch (IOException e) {
            System.out.println("⛔ Помилка збереження бою: " + e.getMessage());
        }
    }

    /**
     * Завантажує стан двох команд і запускає їх на бій.
     */
    public static void loadAndReplayBattle() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {

            // ✅ Небезпечне приведення, але необхідне для десеріалізації
            @SuppressWarnings("unchecked")
            List<Droid> loadedTeam1 = (List<Droid>) ois.readObject();
            @SuppressWarnings("unchecked")
            List<Droid> loadedTeam2 = (List<Droid>) ois.readObject();

            System.out.println("\n*** БІЙ ВІДТВОРЕНО З ФАЙЛА ***");
            System.out.println("Команда 1: " + loadedTeam1.size() + " дроїдів.");
            System.out.println("Команда 2: " + loadedTeam2.size() + " дроїдів.");

            // Запускаємо бій із завантаженими даними
            FightTeamVsTeamManager.startFight(loadedTeam1, loadedTeam2);

        } catch (FileNotFoundException e) {
            System.out.println("⛔ Файл історії бою (" + FILENAME + ") не знайдено. Спочатку збережіть бій (Команда 5).");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⛔ Помилка завантаження або десеріалізації: " + e.getMessage());
        }
    }
}