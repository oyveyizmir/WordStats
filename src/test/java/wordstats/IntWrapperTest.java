package wordstats;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntWrapperTest {

    @Test
    public void getValue() {
        IntWrapper wrapper = new IntWrapper(10);
        assertEquals(10, wrapper.getValue());
    }

    @Test
    public void increment() {
        IntWrapper wrapper = new IntWrapper(100);
        wrapper.increment();
        assertEquals(101, wrapper.getValue());
    }
}