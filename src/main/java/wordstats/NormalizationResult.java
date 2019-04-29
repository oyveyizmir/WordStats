package wordstats;

public class NormalizationResult {
    public final String word;
    public final PartOfSpeech partOfSpeech;
    public final String details;

    public NormalizationResult(String word) {
        this(word, null, null);
    }

    public NormalizationResult(String word, PartOfSpeech partOfSpeech, String details) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.details = details;
    }
}
