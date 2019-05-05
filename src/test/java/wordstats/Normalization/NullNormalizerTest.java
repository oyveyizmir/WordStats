package wordstats.Normalization;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NullNormalizerTest {
    private NullNormalizer normalizer;

    @Before
    public void before() {
        normalizer = new NullNormalizer();
    }

    @Test
    public void normalize() {
        List<NormalizedWord> result = normalizer.normalize("words");

        assertEquals(1, result.size());
        assertEquals("words", result.get(0).word);
        assertNull(result.get(0).partOfSpeech);
    }

    @Test(expected = IllegalArgumentException.class)
    public void normalize_NullWord() {
        normalizer.normalize(null);
    }
}