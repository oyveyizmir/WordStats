package wordstats.Normalization;

import java.util.List;

public interface Normalizer {
    List<NormalizedWord> normalize(String word) throws Exception;
}
