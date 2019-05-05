package wordstats.Normalization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NullNormalizer implements Normalizer {
    @Override
    public List<NormalizedWord> normalize(String word) {
        if (word == null)
            throw new IllegalArgumentException("word");

        //return Collections.singletonList(new NormalizedWord(word.toLowerCase()));
        ArrayList<NormalizedWord> result = new ArrayList<>();
        result.add(new NormalizedWord(word.toLowerCase()));
        return result;
    }
}
