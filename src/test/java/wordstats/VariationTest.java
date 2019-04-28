package wordstats;

import org.junit.Test;

import static org.junit.Assert.*;

public class VariationTest {
    @Test
    public void constructor() {
        Variation variation = new Variation("Wort");

        assertEquals(1, variation.getCount());
        assertEquals("Wort", variation.getWord());
    }
}
