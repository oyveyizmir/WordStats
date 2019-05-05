package wordstats.Normalization;

import wordstats.PartOfSpeech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NormalizedWord {
    private List<String> morphDetails;

    public final String word;
    public final PartOfSpeech partOfSpeech;

    public NormalizedWord(String word) {
        this(word, null);
    }

    public NormalizedWord(String word, PartOfSpeech partOfSpeech) {
        if (word == null)
            throw new IllegalArgumentException("word");

        this.word = word;
        this.partOfSpeech = partOfSpeech;
    }

    public List<String> getMorphDetails() {
        if (morphDetails == null)
            morphDetails = Collections.emptyList();

        return morphDetails;
    }

    public void addMorphDetail(String morphDetail) {
        if (morphDetail == null)
            throw new IllegalArgumentException("morphDetail");

        if (morphDetails == null || morphDetails.isEmpty())
            morphDetails = new ArrayList<>();

        morphDetails.add(morphDetail);
    }
}
