package BigTask_Refactoring.car;

public class Sedan extends Car {

    public static final int MAX_TRUCK_SPEED = 120;

    public Sedan(int numberOfPassengers) {
        super(SEDAN, numberOfPassengers);
    }

    @Override
    public int getMaxSpeed() {
        return MAX_TRUCK_SPEED;
    }
}
