package core.toys;

public class Car extends Toy {
    private int wheels;

    public Car(String name, String size, int wheels, float price, String color) {
        // Виклик конструктора батьківського класу Toy
        super(name, size, price, color);
        // Ініціалізація власного поля
        this.wheels = wheels;
    }


/**
 * Реалізація абстрактного методу getInfo().
 * Цей метод бере базову інформацію з батьківського toString()
 * і доповнює її унікальними даними про машинку.
 * @return Повний текстовий опис іграшки.
 */
    @Override
    public String getInfo() {
        return super.toString() + ", Тип: Машинка, К-ть коліс: " + wheels;
    }


}
