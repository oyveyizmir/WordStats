package wordstats;

import wordstats.Configuration.CommandLineParser;
import wordstats.Configuration.Settings;
import wordstats.Counter.WordCounter;
import wordstats.Counter.WordEntry;
import wordstats.Normalization.*;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new CommandLineParser();
        Settings conf = parser.ParseArgs(args);

        WordReader reader = new WordReader(System.in);
        WordCounter counter = new WordCounter();
        MajkaNormalizer  majkaNormalizer = new MajkaNormalizer(conf.language);
        majkaNormalizer.initialize();
        Normalizer normalizer = new CacheNormalizer(majkaNormalizer);
        //Normalizer normalizer = majkaNormalizer;
        //Normalizer normalizer = new NullNormalizer();

        String word;
        while ((word = reader.getNextWord()) != null) {
            System.err.println("WORD " + word);

            List<NormalizedWord> normWords = normalizer.normalize(word);
            for (NormalizedWord normWord : normWords)
                counter.count(normWord, word);
        }

        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        for (WordEntry entry : counter.getSortedEntries()) {
            out.print(entry.getWord() + "," + entry.getCount() + ",[");
            int posIndex = 0;
            for (PartOfSpeech pos : entry.getPartsOfSpeech().keySet()) {
                if (posIndex > 0)
                    out.print(",");
                out.print(pos);
                posIndex++;
            }
            out.print("],[");
            int varIndex = 0;
            for (String v : entry.getVariations().keySet()) {
                if (varIndex > 0)
                    out.print(",");
                out.print(v);
                varIndex++;
            }
            out.println("]");
            //TODO: add variations
        }
    }
}
