package wordstats.Normalization;

import java.util.ArrayList;
import java.util.List;

public class NullNormalizer implements Normalizer {
    @Override
    public List<NormalizedWord> normalize(String word) {
        ArrayList<NormalizedWord> result = new ArrayList<>();
        result.add(new NormalizedWord(word.toLowerCase()));
        return result;
    }
}
