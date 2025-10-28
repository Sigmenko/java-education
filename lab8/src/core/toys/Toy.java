package core.toys;

import java.io.Serializable;

public abstract class Toy implements Serializable {
    private String name;
    private String size;
    private float price;
    private String color;

    public Toy(String name, String size, float price, String color) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.color = color;
    }

    public abstract String getInfo();

    @Override
    public String toString() {
        return String.format("Назва: %s, Розмір: %s, Колір: %s, Ціна: %.2f грн",
                name, size, color, price);
    }

    public String getName() { return name; }
    public float getPrice() { return price; }
    public String getSize() { return size; }
}