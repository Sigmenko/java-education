package core.commands;

import core.Playroom;
import util.AppLogger;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SortToysCommand implements Command {
    private static final Logger logger = AppLogger.getLogger();
    private final Playroom playroom;

    public SortToysCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        // Перевірка на порожній список буде всередині playroom.sortToys
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сортувати за: 1 - Ціною, 2 - Розміром");
        System.out.print("Ваш вибір: ");
        String choice = scanner.nextLine();
        logger.log(Level.FINE, "Обрано сортування: {0}", choice);
        playroom.sortToys(choice);
    }
}