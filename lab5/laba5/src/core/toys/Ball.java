package core.toys;

public class Ball extends Toy {
    private float diameter;

    public Ball(String name, String color, float diameter, float price, String size) {
        super(name, color, price, size);
        this.diameter = diameter;
    }

    @Override
    public String getInfo() {
        return super.toString() + ", Тип: М'яч, Діаметр: " + diameter + " см";
    }

}
