public class Car {
    private  String model;
    private int speed;
    private int price;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Car(String model, int speed, int price) {
        this.model = model;
        this.speed = speed;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s, max speed: %s, price: %s$",model,speed,price);
    }
}
