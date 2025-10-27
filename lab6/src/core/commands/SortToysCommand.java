package core.commands;

import core.Playroom;
import java.util.Scanner; // Додано імпорт

public class SortToysCommand implements Command {
    private final Playroom playroom;

    public SortToysCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        // Тепер Scanner тут
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сортувати за: 1 - Ціною, 2 - Розміром");
        System.out.print("Ваш вибір: ");
        String choice = scanner.nextLine();

        // Передаємо вибір у метод Playroom
        playroom.sortToys(choice);
    }
}