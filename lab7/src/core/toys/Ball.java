package core.toys;

public class Ball extends Toy {
    private float diameter;

    public Ball(String name, String size, float price, String color, float diameter) {
        super(name, size, price, color);
        this.diameter = diameter;
    }

    @Override
    public String getInfo() {
        return super.toString() + ", Тип: М'яч, Діаметр: " + diameter + " см";
    }
}