package StringParser;

public class Count {
    public static int count;

    public Count(int count) {
        this.count = count;
    }

    public static int getCount() {
        return count;
    }

    public static void increment(){
        count++;
    }
}
