package BigTask_Refactoring.car;

public class Truck extends Car {

    public static final int MAX_TRUCK_SPEED = 80;

    public Truck(int numberOfPassengers) {
        super(CABRIOLET, numberOfPassengers);
    }

    @Override
    public int getMaxSpeed() {
        return MAX_TRUCK_SPEED;
    }
}
