package wordstats.Normalization;

import org.junit.Test;
import wordstats.PartOfSpeech;

import static org.junit.Assert.*;

public class NormalizedWordTest {
    @Test
    public void constructor_1Argument() {
        NormalizedWord normalizedWord = new NormalizedWord("word");

        assertEquals("word", normalizedWord.word);
        assertNull(normalizedWord.partOfSpeech);
        assertTrue(normalizedWord.getMorphDetails().isEmpty());
    }

    @Test
    public void constructor_2Arguments() {
        NormalizedWord normalizedWord = new NormalizedWord("word", PartOfSpeech.Adjective);

        assertEquals("word", normalizedWord.word);
        assertEquals(PartOfSpeech.Adjective, normalizedWord.partOfSpeech);
        assertTrue(normalizedWord.getMorphDetails().isEmpty());
    }

    @Test
    public void constructor_NullPartOfSpeech() {
        NormalizedWord normalizedWord = new NormalizedWord("word", null);

        assertEquals("word", normalizedWord.word);
        assertNull(normalizedWord.partOfSpeech);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_NullWord() {
        new NormalizedWord(null);
    }

    @Test
    public void addMorphDetail() {
        NormalizedWord normalizedWord = new NormalizedWord("gone", PartOfSpeech.Verb);
        normalizedWord.addMorphDetail("VERB");
        normalizedWord.addMorphDetail("PAST PART");

        assertEquals(2, normalizedWord.getMorphDetails().size());
        assertEquals("VERB", normalizedWord.getMorphDetails().get(0));
        assertEquals("PAST PART", normalizedWord.getMorphDetails().get(1));
    }
}