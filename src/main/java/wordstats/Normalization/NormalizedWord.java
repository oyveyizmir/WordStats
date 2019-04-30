package wordstats.Normalization;

import wordstats.PartOfSpeech;

public class NormalizedWord {
    public final String word;
    public final PartOfSpeech partOfSpeech;
    public final String morphDetails;

    public NormalizedWord(String word) {
        this(word, null, null);
    }

    public NormalizedWord(String word, PartOfSpeech partOfSpeech, String details) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.morphDetails = details;
    }
}
