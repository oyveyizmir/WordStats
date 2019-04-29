package wordstats;

import java.io.IOException;
import java.util.List;

public interface Normalizer {
    List<NormalizationResult> normalize(String word) throws Exception;
}
