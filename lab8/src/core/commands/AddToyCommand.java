package core.commands;

import core.Playroom;
import core.toys.*;
import util.AppLogger; // Імпорт логера
import java.util.InputMismatchException; // Імпорт винятку
import java.util.Scanner;
import java.util.logging.Level; // Імпорт рівнів
import java.util.logging.Logger;

public class AddToyCommand implements Command {
    private static final Logger logger = AppLogger.getLogger(); // Отримуємо логер
    private final Playroom playroom;

    public AddToyCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        logger.info("\n--- Початок додавання нової іграшки ---");
        System.out.println("Яку іграшку додати? 1 - Машинка, 2 - Лялька, 3 - М'яч, 4 - Кубик");
        System.out.print("Ваш вибір: ");
        String choice = scanner.nextLine();
        logger.log(Level.FINE, "Тип іграшки обрано: {0}", choice);

        // 👇 ВАЖЛИВО: Весь блок вводу та створення обгорнуто в try
        try {
            System.out.print("Назва: ");
            String name = scanner.nextLine();
            System.out.print("Розмір (Маленький/Середній/Великий): ");
            String size = scanner.nextLine();
            System.out.print("Ціна: ");
            // Саме цей рядок може викинути InputMismatchException
            float price = scanner.nextFloat();
            scanner.nextLine(); // Очистка буфера після nextFloat()
            System.out.print("Колір: ");
            String color = scanner.nextLine();

            Toy newToy = null;
            switch (choice) {
                case "1":
                    System.out.print("Кількість коліс: ");
                    // Цей рядок теж може викинути InputMismatchException
                    int wheels = scanner.nextInt();
                    scanner.nextLine(); // Очистка буфера після nextInt()
                    newToy = new Car(name, size, price, color, wheels);
                    break;
                case "2":
                    System.out.print("Колір волосся: ");
                    String hairColor = scanner.nextLine();
                    newToy = new Doll(name, size, price, color, hairColor);
                    break;
                case "3":
                    System.out.print("Діаметр (см): ");
                    // Цей рядок теж може викинути InputMismatchException
                    float diameter = scanner.nextFloat();
                    scanner.nextLine(); // Очистка буфера після nextFloat()
                    newToy = new Ball(name, size, price, color, diameter);
                    break;
                case "4":
                    System.out.print("Форма: ");
                    String shape = scanner.nextLine();
                    newToy = new Block(name, size, price, color, shape);
                    break;
                default:
                    logger.warning("❌ Невірний тип іграшки обрано: " + choice);
                    System.out.println("❌ Невірний тип іграшки.");
                    break;
            }

            if (newToy != null) {
                logger.log(Level.FINE, "Створено об''єкт іграшки: {0}", newToy.getName());
                playroom.addToy(newToy);
            }
            // 👇 ВАЖЛИВО: Блок catch для перехоплення помилки вводу
        } catch (InputMismatchException e) {
            // Логуємо помилку як SEVERE
            logger.log(Level.SEVERE, "Помилка вводу користувача (очікувалося число)", e);
            // Повідомляємо користувача
            System.out.println("❌ Помилка: Ви ввели не числове значення. Спробуйте додати іграшку ще раз.");
            // Очищаємо залишки некоректного вводу зі сканера!
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        } catch (Exception e) { // Ловимо інші можливі помилки
            logger.log(Level.SEVERE, "Неочікувана помилка при додаванні іграшки", e);
            System.out.println("❌ Сталася неочікувана помилка. Дивіться деталі в логах.");
            if (scanner.hasNextLine()) { // На про всяк випадок
                scanner.nextLine();
            }
        }
    }
}