package app;

import models.Car;
import services.CarService;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Car[] cars = {
                new Car(1, "Toyota", 2015, 12000, "AA1234BB"),
                new Car(2, "BMW", 2018, 25000, "BC5678CD"),
                new Car(3, "Toyota", 2010, 8000, "CE9876DD"),
                new Car(4, "Audi",   2015, 15000, "AA9999AA"),
                new Car(5, "Toyota", 2018, 18000, "KA1111CC")
        };

        CarService carService = new CarService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nОберіть критерій для пошуку:");
            System.out.println("1. Список автомобілів за моделлю");
            System.out.println("2. Список автомобілів, що експлуатуються більше N років");
            System.out.println("3. Список автомобілів за роком та ціною");
            System.out.println("0. Вийти");

            System.out.print("Ваш вибір: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Некоректний вибір. Будь ласка, введіть число.");
                scanner.nextLine();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine(); // буфер

            switch (choice) {
                case 1:
                    System.out.print("Введіть модель: ");
                    String model = scanner.nextLine();
                    List<Car> byModel = carService.findByModel(cars, model);
                    printResults(byModel);
                    break;
                case 2:
                    System.out.print("Введіть мінімальний вік (років): ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Некоректний вік. Будь ласка, введіть ціле число.");
                        scanner.nextLine();
                        break;
                    }
                    int minAge = scanner.nextInt();
                    List<Car> byAge = carService.findByAge(cars, minAge);
                    printResults(byAge);
                    break;
                case 3:
                    System.out.print("Введіть рік випуску: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Некоректний рік. Будь ласка, введіть ціле число.");
                        scanner.nextLine();
                        break;
                    }
                    int year = scanner.nextInt();
                    System.out.print("Введіть мінімальну ціну: ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Некоректна ціна. Будь ласка, введіть число.");
                        scanner.nextLine();
                        break;
                    }
                    double price = scanner.nextDouble();
                    List<Car> byYearAndPrice = carService.findByYearAndPrice(cars, year, price);
                    printResults(byYearAndPrice);
                    break;
                case 0:
                    running = false;
                    System.out.println("Програма завершена.");
                    break;
                default:
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
                    break;
            }
        }
        scanner.close();
    }

    private static void printResults(List<Car> cars) {
        if (cars.isEmpty()) {
            System.out.println("Результатів не знайдено.");
        } else {
            System.out.println("Знайдені автомобілі:");
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }
}