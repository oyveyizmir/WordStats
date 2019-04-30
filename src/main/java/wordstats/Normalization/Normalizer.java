package wordstats.Normalization;

import java.util.List;

public interface Normalizer {
    List<NormalizationResult> normalize(String word) throws Exception;
}
