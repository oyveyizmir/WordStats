package wordstats.Counter;

import org.junit.Before;
import org.junit.Test;
import wordstats.Normalization.NormalizedWord;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class WordCounterTest {
    private WordCounter counter;

    @Before
    public void before() {
        counter = new WordCounter();
    }

    @Test
    public void count() {
        Stream.of("Tree", "one", "x", "TREE", "oNe", "trEE")
                .forEach(x -> counter.count(new NormalizedWord(x.toLowerCase()), x));

        List<WordEntry> entries = counter.getSortedEntries();

        assertEquals(3, entries.size());

        assertEquals("tree", entries.get(0).getWord());
        assertEquals(3, entries.get(0).getCount());

        assertEquals("one", entries.get(1).getWord());
        assertEquals(2, entries.get(1).getCount());

        assertEquals("x", entries.get(2).getWord());
        assertEquals(1, entries.get(2).getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void count_NullNormWord() {
        counter.count(null, "x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void count_NullVariation() {
        counter.count(new NormalizedWord("x"), null);
    }
}
