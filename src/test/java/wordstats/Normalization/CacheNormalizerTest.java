package wordstats.Normalization;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheNormalizerTest {
    @Mock
    private Normalizer mockNormalizer;

    private CacheNormalizer cacheNormalizer;

    @Before
    public void before() throws Exception {
        cacheNormalizer = new CacheNormalizer(mockNormalizer);

        when(mockNormalizer.normalize("boxes")).thenReturn(
                Arrays.asList(new NormalizedWord("box")));
    }

    @Test
    public void normalize() throws Exception {
        for (int i = 0; i < 2; i++) {
            List<NormalizedWord> result = cacheNormalizer.normalize("boxes");

            assertEquals(1, result.size());
            assertEquals("box", result.get(0).word);
            verify(mockNormalizer, times(1)).normalize("boxes");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void normalize_NullWord() throws Exception {
        cacheNormalizer.normalize(null);
    }
}