package wordstats;

import java.util.ArrayList;
import java.util.List;

public class NullNormalizer implements Normalizer {
    @Override
    public List<NormalizationResult> normalize(String word) {
        ArrayList<NormalizationResult> result = new ArrayList<>();
        result.add(new NormalizationResult(word));
        return result;
    }
}
