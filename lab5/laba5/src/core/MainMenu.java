package core;

import core.commands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainMenu {

    private final Map<String, Command> commands = new HashMap<>();

    public MainMenu(Playroom playroom) {
        // Ініціалізуємо всі наші команди
        commands.put("1", new AddToyCommand(playroom));
        commands.put("2", new RemoveToyCommand(playroom));
        commands.put("3", new SortToysCommand(playroom));
        commands.put("4", new FindToysByRangeCommand(playroom));
        commands.put("5", new DisplayToysCommand(playroom));
        commands.put("0", new ExitCommand());
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        String choice;

        while (true) {
            System.out.println("\n===== МЕНЮ ІГРОВОЇ КІМНАТИ =====");
            System.out.println("1. Додати іграшку");
            System.out.println("2. Видалити іграшку");
            System.out.println("3. Сортувати іграшки");
            System.out.println("4. Шукати іграшки за діапазоном");
            System.out.println("5. Переглянути список іграшок");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            choice = scanner.nextLine();

            Command command = commands.get(choice);

            if (command != null) {
                command.execute();
                if (command instanceof ExitCommand) {
                    break; // Виходимо з циклу, якщо це команда виходу
                }
            } else {
                System.out.println("!!! Невірний вибір. Спробуйте ще раз. !!!");
            }
        }
        scanner.close();
    }
}