package core;

import core.commands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainMenu {
    private final Playroom playroom;
    private final Map<String, Command> commands = new HashMap<>();

    public MainMenu(Playroom playroom) {
        this.playroom = playroom;
        commands.put("1", new AddToyCommand(this.playroom));
        commands.put("2", new DisplayToysCommand(this.playroom));
        commands.put("3", new RemoveToyCommand(this.playroom)); // Додано команду
        commands.put("4", new SortToysCommand(this.playroom));
        commands.put("5", new FindToysByRangeCommand(this.playroom));
        commands.put("6", new SaveToFileCommand(this.playroom));
        commands.put("7", new LoadFromFileCommand(this.playroom));
        commands.put("0", new ExitCommand());
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        String choice;

        System.out.println("Ласкаво просимо до Ігрової кімнати!");
        this.playroom.loadFromFile();

        while (true) {
            System.out.println("\n===== МЕНЮ ІГРОВОЇ КІМНАТИ =====");
            System.out.println("1. Додати іграшку");
            System.out.println("2. Переглянути список іграшок");
            System.out.println("3. Видалити іграшку"); // Додано пункт меню
            System.out.println("4. Сортувати іграшки");
            System.out.println("5. Шукати іграшки за діапазоном цін");
            System.out.println("6. Зберегти у файл");
            System.out.println("7. Завантажити з файлу");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            choice = scanner.nextLine();
            Command command = commands.get(choice);

            if (command != null) {
                command.execute();
                if (command instanceof ExitCommand) {
                    break;
                }
            } else {
                System.out.println("❌ Невірний вибір. Спробуйте ще раз.");
            }
        }
    }
}