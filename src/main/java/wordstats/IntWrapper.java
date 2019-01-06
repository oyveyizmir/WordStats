package wordstats;

public class IntWrapper {
    private int value;

    public IntWrapper(int value) { this.value = value; }

    public int getValue() { return value; }

    public void increment() { value++; }
}
