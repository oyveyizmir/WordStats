package wordstats.Counter;

import wordstats.IntWrapper;
import wordstats.PartOfSpeech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordEntry {
    private final String word;
    private int totalCount;
    private Map<String, IntWrapper> variations = new HashMap<>();
    private Map<PartOfSpeech, IntWrapper> partsOfSpeech = new HashMap<>();
    private ArrayList<Variation> sortedVariations = new ArrayList<>();

    public WordEntry(String word) {
        this.word = word;
    }

    public String getWord() { return word; }

    public int getCount() { return totalCount; }

    public Map<String, IntWrapper> getVariations() { return variations; }

    public Map<PartOfSpeech, IntWrapper> getPartsOfSpeech() { return partsOfSpeech; }

    public void count(String variation, PartOfSpeech partOfSpeech) {
        System.out.println("COUNTING WORD " + word + " VAR " + variation + " POS " + partOfSpeech);

        countVariation(variation);

        if (partOfSpeech != null)
            countPartOfSpeech(partOfSpeech);

        totalCount++;
    }

    private void countVariation(String word) {
        incrementCounter(variations, word);
    }

    private void countPartOfSpeech(PartOfSpeech partOfSpeech) {
        incrementCounter(partsOfSpeech, partOfSpeech);
    }

    private static <T> void incrementCounter(Map<T, IntWrapper> map, T key) {
        IntWrapper count = map.get(key);
        if (count == null)
            map.put(key, new IntWrapper(1));
        else
            count.increment();
    }
}