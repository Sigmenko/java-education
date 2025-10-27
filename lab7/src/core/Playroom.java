package core; // Пакет

import core.toys.Toy; // Імпорти
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
// import java.util.Scanner; // Цей імпорт тут вже НЕ потрібен

public class Playroom {
    // Поля класу
    private float budget;
    private List<Toy> toys = new ArrayList<>();
    // ЗМІНЕНО: Зробили шлях до файлу не константою
    private String saveFilePath = "playroom.ser";

    // Конструктор класу
    public Playroom(float budget) {
        this.budget = budget;
    }

    // --- (ОПЦІОНАЛЬНО) Метод для зміни шляху до файлу збереження (для тестів) ---
    public void setSaveFilePath(String path) {
        this.saveFilePath = path;
    }

    // --- Метод додавання ---
    public void addToy(Toy toy) {
        if (toy.getPrice() <= budget) {
            toys.add(toy);
            budget -= toy.getPrice();
            System.out.printf("✅ Іграшку '%s' додано. Залишок бюджету: %.2f грн\n", toy.getName(), budget);
        } else {
            System.out.printf("❌ Недостатньо коштів. Потрібно %.2f, а є %.2f\n", toy.getPrice(), budget);
        }
    }

    // --- Метод відображення ---
    public void displayToys() {
        if (toys.isEmpty()) {
            System.out.println("Ігрова кімната порожня.");
            return;
        }
        System.out.println("\n--- Список іграшок у кімнаті ---");
        for (Toy toy : toys) {
            System.out.println(toy.getInfo());
        }
        System.out.println("---------------------------------");
    }

    // --- Оновлені методи ---

    // === Нова версія sortToys (приймає вибір) ===
    public void sortToys(String choice) {
        if (toys.isEmpty()) {
            System.out.println("Нічого сортувати, кімната порожня.");
            return;
        }

        if ("1".equals(choice)) {
            toys.sort(Comparator.comparing(Toy::getPrice));
            System.out.println("Іграшки відсортовано за ціною.");
        } else if ("2".equals(choice)) {
            toys.sort(Comparator.comparing(Toy::getSize));
            System.out.println("Іграшки відсортовано за розміром.");
        } else {
            System.out.println("Невірний вибір для сортування.");
        }
        displayToys();
    }

    // === Нова версія findToysByPriceRange (приймає min і max) ===
    public void findToysByPriceRange(float minPrice, float maxPrice) {
        if (toys.isEmpty()) {
            System.out.println("Нічого шукати, кімната порожня.");
            return;
        }

        System.out.printf("\n--- Іграшки в діапазоні цін від %.2f до %.2f ---\n", minPrice, maxPrice);
        boolean found = false;
        for (Toy toy : toys) {
            if (toy.getPrice() >= minPrice && toy.getPrice() <= maxPrice) {
                System.out.println(toy.getInfo());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Іграшок у заданому діапазоні не знайдено.");
        }
        System.out.println("-------------------------------------------------");
    }

    // === Нова версія removeToy (приймає назву) ===
    public void removeToy(String nameToRemove) {
        if (toys.isEmpty()) {
            System.out.println("Нічого видаляти, кімната порожня.");
            return;
        }

        var iterator = toys.iterator();
        boolean removed = false;
        while (iterator.hasNext()) {
            Toy toy = iterator.next();
            if (toy.getName().equalsIgnoreCase(nameToRemove)) {
                iterator.remove();
                budget += toy.getPrice();
                System.out.printf("✅ Іграшку '%s' видалено. Бюджет оновлено: %.2f грн\n", nameToRemove, budget);
                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("❌ Іграшку з назвою '" + nameToRemove + "' не знайдено.");
        }
    }

    // --- Методи для роботи з файлами ---
    public void saveToFile() {
        // ЗМІНЕНО: Використовуємо змінну saveFilePath
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
            oos.writeObject(toys);
            oos.writeFloat(budget);
            System.out.println("✅ Стан ігрової кімнати збережено у файл: " + saveFilePath);
        } catch (IOException e) {
            System.out.println("❌ Помилка при збереженні у файл: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        // ЗМІНЕНО: Використовуємо змінну saveFilePath
        File file = new File(saveFilePath);
        if (!file.exists()) {
            System.out.println("Файл для завантаження не знайдено. Починаємо з порожньою кімнатою.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
            toys = (List<Toy>) ois.readObject();
            budget = ois.readFloat();
            System.out.println("✅ Стан ігрової кімнати завантажено з файлу.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Помилка при завантаженні з файлу: " + e.getMessage());
        }
    }

    // --- Геттер для бюджету ---
    public float getBudget() {
        return budget;
    }

    // --- (Опціонально) Геттер для списку іграшок (повертає копію!) ---
    // Потрібен для деяких тестів
    public List<Toy> getToys() {
        return new ArrayList<>(toys); // Повертаємо копію!
    }

} // Кінець класу Playroom