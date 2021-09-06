package BigTask_Refactoring.car;

public class Cabriolet extends Car {

    public static final int MAX_TRUCK_SPEED = 90;

    public Cabriolet(int numberOfPassengers) {
        super(CABRIOLET, numberOfPassengers);
    }

    @Override
    public int getMaxSpeed() {
        return MAX_TRUCK_SPEED;
    }
}
