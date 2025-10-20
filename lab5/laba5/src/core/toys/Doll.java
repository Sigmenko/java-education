package core.toys;

public class Doll extends Toy {
    private String hairColor;

    public Doll(String name, String size, float price, String color, String hairColor) {
        super(name, size, price, color);
        this.hairColor = hairColor;
    }


    @Override
    public String getInfo() {
        return super.toString() + ", Тип: Лялька, Колір волосся: " + hairColor;
    }
}