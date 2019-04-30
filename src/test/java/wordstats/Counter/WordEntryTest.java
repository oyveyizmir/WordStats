package wordstats.Counter;

import org.junit.Test;
import wordstats.Counter.WordEntry;

import static org.junit.Assert.*;

public class WordEntryTest {
    @Test
    public void constructor() {
        WordEntry entry = new WordEntry("word");

        assertEquals(0, entry.getCount());

        assertEquals("word", entry.getWord());
    }

    @Test
    public void count() {
        WordEntry entry = new WordEntry("слово");

        entry.count("СЛОВО", null);
        entry.count("Слово", null);
        entry.count("слово", null);

        assertEquals("слово", entry.getWord());
        assertEquals(3, entry.getCount());
    }
}
