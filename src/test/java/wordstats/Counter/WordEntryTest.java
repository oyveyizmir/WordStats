package wordstats.Counter;

import org.junit.Test;
import wordstats.PartOfSpeech;

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
        WordEntry entry = new WordEntry("machen");

        entry.count("gemacht", PartOfSpeech.Verb);
        entry.count("machend", PartOfSpeech.Verb);
        entry.count("gemacht", PartOfSpeech.Verb);

        assertEquals("machen", entry.getWord());
        assertEquals(3, entry.getCount());

        assertEquals(2, entry.getVariations().size());
        assertEquals(2, entry.getVariations().get("gemacht").getValue());
        assertEquals(1, entry.getVariations().get("machend").getValue());

        assertEquals(1, entry.getPartsOfSpeech().size());
        assertEquals(3, entry.getPartsOfSpeech().get(PartOfSpeech.Verb).getValue());
    }
}
