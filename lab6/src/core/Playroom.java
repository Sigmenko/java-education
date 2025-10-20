package core;

import core.toys.Toy;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Playroom {
    private float budget;
    private List<Toy> toys = new ArrayList<>();
    private static final String SAVE_FILE = "playroom.ser";

    public Playroom(float budget) {
        this.budget = budget;
    }

    public void addToy(Toy toy) {
        if (toy.getPrice() <= budget) {
            toys.add(toy);
            budget -= toy.getPrice();
            System.out.printf("✅ Іграшку '%s' додано. Залишок бюджету: %.2f грн\n", toy.getName(), budget);
        } else {
            System.out.printf("❌ Недостатньо коштів. Потрібно %.2f, а є %.2f\n", toy.getPrice(), budget);
        }
    }

    // --- НОВИЙ МЕТОД ---
    public void removeToy() {
        if (toys.isEmpty()) {
            System.out.println("Нічого видаляти, кімната порожня.");
            return;
        }

        displayToys(); // Показуємо список, щоб користувач знав, що видаляти
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть назву іграшки, яку потрібно видалити: ");
        String nameToRemove = scanner.nextLine();

        var iterator = toys.iterator();
        boolean removed = false;
        while (iterator.hasNext()) {
            Toy toy = iterator.next();
            if (toy.getName().equalsIgnoreCase(nameToRemove)) {
                iterator.remove(); // Видаляємо знайдений елемент
                budget += toy.getPrice(); // Повертаємо гроші в бюджет
                System.out.printf("✅ Іграшку '%s' видалено. Бюджет оновлено: %.2f грн\n", nameToRemove, budget);
                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("❌ Іграшку з назвою '" + nameToRemove + "' не знайдено.");
        }
    }

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

    public void sortToys() {
        if (toys.isEmpty()) {
            System.out.println("Нічого сортувати, кімната порожня.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сортувати за: 1 - Ціною, 2 - Розміром");
        System.out.print("Ваш вибір: ");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            toys.sort(Comparator.comparing(Toy::getPrice));
            System.out.println("Іграшки відсортовано за ціною.");
        } else if ("2".equals(choice)) {
            toys.sort(Comparator.comparing(Toy::getSize));
            System.out.println("Іграшки відсортовано за розміром.");
        } else {
            System.out.println("Невірний вибір.");
        }
        displayToys();
    }

    public void findToysByPriceRange() {
        if (toys.isEmpty()) {
            System.out.println("Нічого шукати, кімната порожня.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть мінімальну ціну: ");
        float minPrice = scanner.nextFloat();
        System.out.print("Введіть максимальну ціну: ");
        float maxPrice = scanner.nextFloat();

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

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(toys);
            oos.writeFloat(budget);
            System.out.println("✅ Стан ігрової кімнати збережено у файл: " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("❌ Помилка при збереженні у файл: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("Файл для завантаження не знайдено. Починаємо з порожньою кімнатою.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            toys = (List<Toy>) ois.readObject();
            budget = ois.readFloat();
            System.out.println("✅ Стан ігрової кімнати завантажено з файлу.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Помилка при завантаженні з файлу: " + e.getMessage());
        }
    }
}