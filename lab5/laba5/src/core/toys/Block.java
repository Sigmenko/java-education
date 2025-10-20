package core.toys;

public class Block extends Toy{
    private String shape;
    public Block(String name, String color, String size, String shape, float price){
        super(name, color, price, size);
        this.shape = shape;
    }
    @Override
    public String getInfo() {
        return super.toString() + ", Тип: Кубик, Форма: " + shape;
    }

}
