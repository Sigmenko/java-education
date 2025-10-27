package core.toys;

public class Car extends Toy {
    private int wheels;

    public Car(String name, String size, float price, String color, int wheels) {
        super(name, size, price, color);
        this.wheels = wheels;
    }

    @Override
    public String getInfo() {
        return super.toString() + ", Тип: Машинка, К-ть коліс: " + wheels;
    }
}