package core.commands;

import core.Playroom;
import util.AppLogger;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FindToysByRangeCommand implements Command {
    private static final Logger logger = AppLogger.getLogger();
    private final Playroom playroom;

    public FindToysByRangeCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        // Перевірка на порожній список буде всередині playroom.findToys...
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введіть мінімальну ціну: ");
            float minPrice = scanner.nextFloat();
            System.out.print("Введіть максимальну ціну: ");
            float maxPrice = scanner.nextFloat();
            scanner.nextLine(); // Очистка буфера
            logger.log(Level.FINE, "Запит на пошук іграшок в діапазоні: {0} - {1}", new Object[]{minPrice, maxPrice});
            playroom.findToysByPriceRange(minPrice, maxPrice);
        } catch (InputMismatchException e) {
            logger.log(Level.SEVERE, "Помилка вводу користувача (очікувалося число)", e);
            System.out.println("❌ Помилка: Ви ввели не числове значення. Спробуйте ще раз.");
            if (scanner.hasNextLine()) scanner.nextLine(); // Очистка буфера
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Неочікувана помилка при пошуку іграшок", e);
            System.out.println("❌ Сталася неочікувана помилка. Дивіться деталі в логах.");
        }
    }
}