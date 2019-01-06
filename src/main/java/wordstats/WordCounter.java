package wordstats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordCounter {
    private HashMap<String, WordEntry> words = new HashMap<>();
    private ArrayList<WordEntry> sortedWords = new ArrayList<>();

    public void count(String word) {
        String key = word.toLowerCase();
        WordEntry wordEntry = words.get(key);
        if (wordEntry == null) {
            wordEntry = new WordEntry();
            words.put(key, wordEntry);
        }
        wordEntry.count(word);
    }

    public void sort() {

    }

    public List<WordEntry> getWords() { return sortedWords; }
}
