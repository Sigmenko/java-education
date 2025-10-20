package core.commands;

import core.Playroom;
import core.toys.*;
import java.util.Scanner;

public class AddToyCommand implements Command {
    private final Playroom playroom;

    public AddToyCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Додавання нової іграшки ---");
        System.out.println("Яку іграшку додати? 1 - Машинка, 2 - Лялька, 3 - М'яч, 4 - Кубик");
        System.out.print("Ваш вибір: ");
        String choice = scanner.nextLine();

        System.out.print("Назва: ");
        String name = scanner.nextLine();
        System.out.print("Розмір (Маленький/Середній/Великий): ");
        String size = scanner.nextLine();
        System.out.print("Ціна: ");
        float price = scanner.nextFloat();
        scanner.nextLine(); // Очистка буфера
        System.out.print("Колір: ");
        String color = scanner.nextLine();

        Toy newToy = null;
        switch (choice) {
            case "1":
                System.out.print("Кількість коліс: ");
                int wheels = scanner.nextInt();
                newToy = new Car(name, size, price, color, wheels);
                break;
            case "2":
                System.out.print("Колір волосся: ");
                String hairColor = scanner.nextLine();
                newToy = new Doll(name, size, price, color, hairColor);
                break;
            case "3":
                System.out.print("Діаметр (см): ");
                float diameter = scanner.nextFloat();
                newToy = new Ball(name, size, price, color, diameter);
                break;
            case "4":
                System.out.print("Форма: ");
                String shape = scanner.nextLine();
                newToy = new Block(name, size, price, color, shape);
                break;
            default:
                System.out.println("❌ Невірний тип іграшки.");
                break;
        }

        if (newToy != null) {
            playroom.addToy(newToy);
        }
    }
}