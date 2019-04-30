package wordstats;

import wordstats.Configuration.CommandLineParser;
import wordstats.Configuration.Settings;
import wordstats.Counter.WordCounter;
import wordstats.Counter.WordEntry;
import wordstats.Normalization.MajkaNormalizer;
import wordstats.Normalization.NormalizationResult;

import java.io.PrintStream;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new CommandLineParser();
        Settings conf = parser.ParseArgs(args);

        WordReader reader = new WordReader(System.in);
        WordCounter counter = new WordCounter();
        MajkaNormalizer normalizer = new MajkaNormalizer(conf.language);
        normalizer.initialize();

        String word;
        while ((word = reader.getNextWord()) != null) {
            System.out.println("NORMALIZE: " + word);
            List<NormalizationResult> norm = normalizer.normalize(word);
            for (NormalizationResult res : norm) {
                System.out.println(res.word + "," + res.partOfSpeech + "," + res.details);
            }
            counter.count(word);
        }

        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        for (WordEntry entry : counter.getWords()) {
            out.println(entry.getWord() + "," + entry.getCount());
            //TODO: add variations
        }

    }
}
