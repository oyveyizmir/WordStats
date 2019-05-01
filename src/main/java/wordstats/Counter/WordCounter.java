package wordstats.Counter;

import wordstats.Normalization.NormalizedWord;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WordCounter {
    private HashMap<String, WordEntry> words = new HashMap<>();
    private List<WordEntry> sortedWords;

    public void count(NormalizedWord normWord, String variation) {
        System.err.println("COUNTING WORD " + normWord.word + " VAR " + variation);

        sortedWords = null;
        WordEntry wordEntry = getWordEntry(normWord.word);
        wordEntry.count(variation, normWord.partOfSpeech);
    }

    private WordEntry getWordEntry(String key) {
        //return words.computeIfAbsent(key, WordEntry::new);
        WordEntry entry = words.get(key);
        if (entry == null) {
            entry = new WordEntry(key);
            words.put(key, entry);
        }
        return entry;
    }

    private void sortByFrequency() {
        sortedWords = words.values().stream()
                .sorted((e1, e2) -> e2.getCount() - e1.getCount())
                .collect(Collectors.toList());
    }

    public List<WordEntry> getSortedEntries() {
        if (sortedWords == null)
            sortByFrequency();
        return sortedWords;
    }
}
