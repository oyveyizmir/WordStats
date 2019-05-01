package wordstats.Normalization;

import wordstats.PartOfSpeech;

import java.util.ArrayList;
import java.util.List;

public class NormalizedWord {
    public final String word;
    public final PartOfSpeech partOfSpeech;
    public final List<String> morphDetails = new ArrayList<>();

    public NormalizedWord(String word) {
        this(word, null);
    }

    public NormalizedWord(String word, PartOfSpeech partOfSpeech) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
    }

    public void addMorphDetail(String morphDetail) {
        morphDetails.add(morphDetail);
    }
}
