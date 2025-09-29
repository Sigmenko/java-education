package services;

import models.Car;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class CarService {

    public List<Car> findByModel(Car[] cars, String model) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getModel().equalsIgnoreCase(model)) {
                result.add(car);
            }
        }
        return result;
    }

    public List<Car> findByAge(Car[] cars, int minAge) {
        List<Car> result = new ArrayList<>();
        int currentYear = Year.now().getValue();
        for (Car car : cars) {
            int age = currentYear - car.getYear();
            if (age > minAge) {
                result.add(car);
            }
        }
        return result;
    }

    public List<Car> findByYearAndPrice(Car[] cars, int year, double minPrice) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getYear() == year && car.getPrice() > minPrice) {
                result.add(car);
            }
        }
        return result;
    }
}