package wordstats.Counter;

import wordstats.Normalization.NormalizedWord;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WordCounter {
    private HashMap<String, WordEntry> words = new HashMap<>();
    private List<WordEntry> sortedWords;

    public void count(NormalizedWord normWord, String word) {
        sortedWords = null;
        WordEntry wordEntry = getWordEntry(normWord.word);
        wordEntry.count(word, normWord.partOfSpeech);
    }

    private WordEntry getWordEntry(String key) {
        WordEntry wordEntry = words.get(key);
        if (wordEntry == null) {
            wordEntry = new WordEntry(key);
            words.put(key, wordEntry);
        }
        return wordEntry;
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
