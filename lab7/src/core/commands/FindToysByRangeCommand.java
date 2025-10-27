package core.commands;

import core.Playroom;
import java.util.Scanner; // Додано імпорт

public class FindToysByRangeCommand implements Command {
    private final Playroom playroom;

    public FindToysByRangeCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        // Тепер Scanner тут
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть мінімальну ціну: ");
        float minPrice = scanner.nextFloat();
        System.out.print("Введіть максимальну ціну: ");
        float maxPrice = scanner.nextFloat();
        scanner.nextLine(); // Очистка буфера

        // Передаємо значення у метод Playroom
        playroom.findToysByPriceRange(minPrice, maxPrice);
    }
}