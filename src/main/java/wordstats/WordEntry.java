package wordstats;

import java.util.ArrayList;
import java.util.HashMap;

public class WordEntry {
    private String word;
    private int totalCount;
    private HashMap<String, IntWrapper> variations = new HashMap<String, IntWrapper>();
    private ArrayList<Variation> sortedVariations = new ArrayList<Variation>();

    public String getWord() {
        return word;
    }

    public void count(String word) {
        IntWrapper count = variations.get(word);
        if (count == null)
            variations.put(word, new IntWrapper(1));
        else
            count.increment();
        totalCount++;
    }

    public void postprocess() {
        sortedVariations.sort((a, b) -> b.getCount() - a.getCount());
    }

    public int getCount() { return totalCount; }
}