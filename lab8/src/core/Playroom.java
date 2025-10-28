package core;

import core.toys.Toy;
import util.AppLogger;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Playroom {
    private static final Logger logger = AppLogger.getLogger();
    private float budget;
    private List<Toy> toys = new ArrayList<>();
    private String saveFilePath = "playroom.ser";

    public Playroom(float budget) {
        this.budget = budget;
        logger.log(Level.INFO, "Створено Playroom з бюджетом: {0}", String.format("%.2f", budget));
    }

    public void setSaveFilePath(String path) {
        this.saveFilePath = path;
    }

    public void addToy(Toy toy) {
        if (toy == null) {
            logger.warning("Спроба додати null іграшку.");
            return;
        }
        if (toy.getPrice() <= budget) {
            toys.add(toy);
            budget -= toy.getPrice();
            logger.log(Level.INFO, "✅ Іграшку ''{0}'' додано. Залишок бюджету: {1}",
                    new Object[]{toy.getName(), String.format("%.2f", budget)});
        } else {
            logger.log(Level.WARNING, "❌ Недостатньо коштів для покупки ''{0}''. Потрібно {1}, а є {2}",
                    new Object[]{toy.getName(), String.format("%.2f", toy.getPrice()), String.format("%.2f", budget)});
            System.out.println("❌ Недостатньо коштів."); // Повідомлення користувачу
        }
    }

    public void displayToys() {
        if (toys.isEmpty()) {
            logger.info("Ігрова кімната порожня.");
            System.out.println("Ігрова кімната порожня."); // Повідомлення користувачу
            return;
        }
        logger.fine("\n--- Список іграшок у кімнаті ---");
        System.out.println("\n--- Список іграшок у кімнаті ---");
        for (Toy toy : toys) {
            System.out.println(toy.getInfo());
        }
        System.out.println("---------------------------------");
        logger.fine("--- Кінець списку іграшок ---");
    }

    public void sortToys(String choice) {
        if (toys.isEmpty()) {
            logger.info("Нічого сортувати, кімната порожня.");
            System.out.println("Нічого сортувати, кімната порожня."); // Повідомлення користувачу
            return;
        }

        if ("1".equals(choice)) {
            toys.sort(Comparator.comparing(Toy::getPrice));
            logger.info("Іграшки відсортовано за ціною.");
        } else if ("2".equals(choice)) {
            toys.sort(Comparator.comparing(Toy::getSize));
            logger.info("Іграшки відсортовано за розміром.");
        } else {
            logger.warning("Невірний вибір для сортування: " + choice);
            System.out.println("❌ Невірний вибір для сортування."); // Повідомлення користувачу
            return; // Не показуємо список, якщо вибір невірний
        }
        displayToys(); // Показуємо результат сортування
    }

    public void findToysByPriceRange(float minPrice, float maxPrice) {
        if (toys.isEmpty()) {
            logger.info("Нічого шукати, кімната порожня.");
            System.out.println("Нічого шукати, кімната порожня."); // Повідомлення користувачу
            return;
        }

        logger.log(Level.FINE, "Пошук іграшок в діапазоні цін від {0} до {1}",
                new Object[]{String.format("%.2f", minPrice), String.format("%.2f", maxPrice)});
        System.out.printf("\n--- Іграшки в діапазоні цін від %.2f до %.2f ---\n", minPrice, maxPrice);
        boolean found = false;
        for (Toy toy : toys) {
            if (toy.getPrice() >= minPrice && toy.getPrice() <= maxPrice) {
                System.out.println(toy.getInfo());
                found = true;
            }
        }
        if (!found) {
            logger.info("Іграшок у заданому діапазоні не знайдено.");
            System.out.println("Іграшок у заданому діапазоні не знайдено."); // Повідомлення користувачу
        }
        System.out.println("-------------------------------------------------");
    }

    public void removeToy(String nameToRemove) {
        if (toys.isEmpty()) {
            logger.info("Нічого видаляти, кімната порожня.");
            System.out.println("Нічого видаляти, кімната порожня."); // Повідомлення користувачу
            return;
        }

        var iterator = toys.iterator();
        boolean removed = false;
        while (iterator.hasNext()) {
            Toy toy = iterator.next();
            if (toy.getName().equalsIgnoreCase(nameToRemove)) {
                iterator.remove();
                budget += toy.getPrice();
                logger.log(Level.INFO, "✅ Іграшку ''{0}'' видалено. Бюджет оновлено: {1}",
                        new Object[]{nameToRemove, String.format("%.2f", budget)});
                System.out.printf("✅ Іграшку '%s' видалено.\n", nameToRemove); // Повідомлення користувачу
                removed = true;
                break;
            }
        }

        if (!removed) {
            logger.warning("❌ Іграшку з назвою '" + nameToRemove + "' не знайдено.");
            System.out.println("❌ Іграшку з назвою '" + nameToRemove + "' не знайдено."); // Повідомлення користувачу
        }
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
            oos.writeObject(toys);
            oos.writeFloat(budget);
            logger.log(Level.INFO, "✅ Стан ігрової кімнати збережено у файл: {0}", saveFilePath);
            System.out.println("✅ Стан збережено."); // Повідомлення користувачу
        } catch (IOException e) {
            logger.log(Level.SEVERE, "❌ Помилка при збереженні у файл " + saveFilePath, e);
            System.out.println("❌ Помилка збереження. Дивіться деталі в логах."); // Повідомлення користувачу
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(saveFilePath);
        if (!file.exists()) {
            logger.info("Файл для завантаження не знайдено: " + saveFilePath + ". Починаємо з порожньою кімнатою.");
            // Не повідомляємо користувача тут, бо це очікувано при першому запуску
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
            toys = (List<Toy>) ois.readObject();
            budget = ois.readFloat();
            logger.log(Level.INFO, "✅ Стан ігрової кімнати завантажено з файлу: {0}", saveFilePath);
            System.out.println("✅ Стан завантажено з файлу."); // Повідомлення користувачу
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "❌ Помилка при завантаженні з файлу " + saveFilePath, e);
            System.out.println("❌ Помилка завантаження. Дивіться деталі в логах."); // Повідомлення користувачу
        }
    }

    public float getBudget() {
        return budget;
    }

    public List<Toy> getToys() {
        return new ArrayList<>(toys);
    }
}