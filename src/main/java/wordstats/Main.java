package wordstats;

import wordstats.Configuration.CommandLineParser;
import wordstats.Configuration.Settings;
import wordstats.Counter.WordCounter;
import wordstats.Counter.WordEntry;
import wordstats.Normalization.MajkaNormalizer;
import wordstats.Normalization.NormalizedWord;

import java.io.PrintStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static void Test() {
        Pattern outputPattern = Pattern.compile("((\\p{L}+)|\\?):(\\p{L}+\\d*)");
        Matcher m = outputPattern.matcher("Gutenberg:EIG.NAC.AKK.SIN.FEM.ART");
        if (!m.find()) {
            System.out.println("NOT FOUND");
            return;
        }
        System.out.println(m.group(0));
        System.out.println(m.group(1));
        System.out.println(m.group(2));
        System.out.println(m.group(3));
    }

    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new CommandLineParser();
        Settings conf = parser.ParseArgs(args);

        WordReader reader = new WordReader(System.in);
        WordCounter counter = new WordCounter();
        MajkaNormalizer normalizer = new MajkaNormalizer(conf.language);
        normalizer.initialize();

        String word;
        while ((word = reader.getNextWord()) != null) {
            List<NormalizedWord> normWords = normalizer.normalize(word);
            for (NormalizedWord normWord : normWords)
                counter.count(normWord, word);
        }

        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        for (WordEntry entry : counter.getSortedEntries()) {
            out.print(entry.getWord() + "," + entry.getCount());
            //TODO: add variations
        }
    }
}
