package core;

import core.commands.*;
import util.AppLogger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenu {
    private static final Logger logger = AppLogger.getLogger();
    private final Playroom playroom;
    private final Map<String, Command> commands = new HashMap<>();

    public MainMenu(Playroom playroom) {
        this.playroom = playroom;
        commands.put("1", new AddToyCommand(this.playroom));
        commands.put("2", new DisplayToysCommand(this.playroom));
        commands.put("3", new RemoveToyCommand(this.playroom));
        commands.put("4", new SortToysCommand(this.playroom));
        commands.put("5", new FindToysByRangeCommand(this.playroom));
        commands.put("6", new SaveToFileCommand(this.playroom));
        commands.put("7", new LoadFromFileCommand(this.playroom));
        commands.put("0", new ExitCommand());
        logger.info("Меню ініціалізовано з командами.");
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        String choice;

        logger.info("Ласкаво просимо до Ігрової кімнати!");
        this.playroom.loadFromFile(); // Автоматичне завантаження

        while (true) {
            // Виводимо меню в консоль
            System.out.println("\n===== МЕНЮ ІГРОВОЇ КІМНАТИ =====");
            System.out.println("1. Додати іграшку");
            System.out.println("2. Переглянути список іграшок");
            System.out.println("3. Видалити іграшку");
            System.out.println("4. Сортувати іграшки");
            System.out.println("5. Шукати іграшки за діапазоном цін");
            System.out.println("6. Зберегти у файл");
            System.out.println("7. Завантажити з файлу");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            choice = scanner.nextLine();
            logger.log(Level.FINE, "Користувач обрав: {0}", choice);

            Command command = commands.get(choice);

            if (command != null) {
                try {
                    command.execute();
                    if (command instanceof ExitCommand) {
                        logger.info("Користувач обрав вихід."); // Логуємо вихід тут
                        break; // Вихід з циклу
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Неочікувана помилка при виконанні команди " + choice, e);
                    System.out.println("❌ Сталася неочікувана помилка. Дивіться деталі в логах.");
                }
            } else {
                logger.warning("❌ Невірний вибір користувача: " + choice);
                System.out.println("❌ Невірний вибір. Спробуйте ще раз.");
            }
        }
        // scanner.close(); // Не закриваємо System.in
    }
}