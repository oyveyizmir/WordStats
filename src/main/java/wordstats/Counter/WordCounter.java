package wordstats.Counter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wordstats.Normalization.NormalizedWord;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WordCounter {
    private static Logger logger = LogManager.getLogger();

    private HashMap<String, WordEntry> words = new HashMap<>();
    private List<WordEntry> sortedWords;

    public void count(NormalizedWord normWord, String variation) {
        //logger.debug("Counting word \"" + normWord.word + "\", variation \"" + variation + "\"");
        logger.debug("Counting word \"{}\", part of speech \"{}\", variation \"{}\"",
                normWord.word, normWord.partOfSpeech, variation);

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
