package models;

public class Car {
    private int id;
    private String model;
    private int year;
    private double price;
    private String registrationNumber;

    public Car(int id, String model, int year, double price, String registrationNumber) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.price = price;
        this.registrationNumber = registrationNumber;
    }

    public int getId() { return id; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public String getRegistrationNumber() { return registrationNumber; }

    public void setId(int newId) {
        if (newId <= 0) {
            throw new IllegalArgumentException("Має бути позитивним");
        }
        this.id = newId;
    }

    public void setPrice(double newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("Має бути позитивним ");
        }
        this.price = newPrice;
    }

    public void setModel(String newModel) { this.model = newModel; }
    public void setYear(int newYear) { this.year = newYear; }
    public void setRegistrationNumber(String newRegisterNumber) { this.registrationNumber = newRegisterNumber; }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }
}