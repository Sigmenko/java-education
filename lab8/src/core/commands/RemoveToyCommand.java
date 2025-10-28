package core.commands;

import core.Playroom;
import util.AppLogger;
import java.util.Scanner;
import java.util.logging.Logger;

public class RemoveToyCommand implements Command {
    private static final Logger logger = AppLogger.getLogger();
    private final Playroom playroom;

    public RemoveToyCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        // Логіка перевірки на порожній список тепер всередині playroom.removeToy
        // Тому просто викликаємо метод
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть назву іграшки, яку потрібно видалити: ");
        String nameToRemove = scanner.nextLine();
        logger.fine("Запит на видалення іграшки: " + nameToRemove);
        playroom.removeToy(nameToRemove);
    }
}