package wordstats.Counter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WordCounter {
    private HashMap<String, WordEntry> words = new HashMap<>();
    private List<WordEntry> sortedWords;

    public void count(String word) {
        sortedWords = null;
        String key = word.toLowerCase();
        WordEntry wordEntry = words.get(key);
        if (wordEntry == null) {
            wordEntry = new WordEntry();
            words.put(key, wordEntry);
        }
        wordEntry.count(word);
    }

    private void sortByFrequency() {
        sortedWords = words.values().stream()
                .sorted((e1, e2) -> e2.getCount() - e1.getCount())
                .collect(Collectors.toList());
    }

    public List<WordEntry> getWords() {
        if (sortedWords == null)
            sortByFrequency();
        return sortedWords;
    }
}
