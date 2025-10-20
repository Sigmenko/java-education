package core.toys;

public class Block extends Toy {
    private String shape;

    public Block(String name, String size, float price, String color, String shape) {
        super(name, size, price, color);
        this.shape = shape;
    }

    @Override
    public String getInfo() {
        return super.toString() + ", Тип: Кубик, Форма: " + shape;
    }
}