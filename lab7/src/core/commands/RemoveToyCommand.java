package core.commands;

import core.Playroom;
import java.util.Scanner; // Додано імпорт

public class RemoveToyCommand implements Command {
    private final Playroom playroom;

    public RemoveToyCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        // Спочатку покажемо список
        playroom.displayToys();

        // Тепер Scanner тут
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть назву іграшки, яку потрібно видалити: ");
        String nameToRemove = scanner.nextLine();

        // Передаємо назву у метод Playroom
        playroom.removeToy(nameToRemove);
    }
}