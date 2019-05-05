package wordstats.Normalization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheNormalizer implements Normalizer {
    private Normalizer normalizer;
    private Map<String, List<NormalizedWord>> cache = new HashMap<>();

    public CacheNormalizer(Normalizer normalizer) {
        this.normalizer = normalizer;
    }

    @Override
    public List<NormalizedWord> normalize(String word) throws Exception {
        if (word == null)
            throw new IllegalArgumentException("word");

        List<NormalizedWord> normWords = cache.get(word);
        if (normWords == null) {
            normWords = normalizer.normalize(word);
            cache.put(word, normWords);
        }
        return normWords;
    }
}
