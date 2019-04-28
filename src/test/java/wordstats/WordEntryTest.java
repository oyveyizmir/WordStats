package wordstats;

import org.junit.Test;

import static org.junit.Assert.*;

public class WordEntryTest {
    @Test
    public void constructor() {
        WordEntry entry = new WordEntry();

        assertEquals(0, entry.getCount());

        assertNull(entry.getWord());
    }

    @Test
    public void count() {
        WordEntry entry = new WordEntry();

        entry.count("СЛОВО");
        entry.count("word");
        entry.count("слово");

        assertEquals(3, entry.getCount());
        assertEquals("СЛОВО", entry.getWord());
    }
}
