package wordstats.Counter;

import org.junit.Test;
import wordstats.Normalization.NormalizedWord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class WordCounterTest {
    @Test
    public void countWords() {
        WordCounter counter = new WordCounter();

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

    @Test
    public void test() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        Integer x = map.computeIfAbsent("C", k -> 100);
        System.out.println(map);
    }
}
