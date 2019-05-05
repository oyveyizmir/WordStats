package wordstats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wordstats.Configuration.CommandLineParser;
import wordstats.Configuration.Settings;
import wordstats.Counter.WordCounter;
import wordstats.Counter.WordEntry;
import wordstats.Normalization.*;

import java.io.PrintStream;
import java.util.List;

public class Main {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        logger.info("Starting WordStats");

        CommandLineParser parser = new CommandLineParser();
        Settings conf = parser.ParseArgs(args);

        WordReader reader = new WordReader(System.in);
        WordCounter counter = new WordCounter();
        MajkaNormalizer  majkaNormalizer = new MajkaNormalizer(conf.language);
        majkaNormalizer.initialize();
        CacheNormalizer normalizer = new CacheNormalizer(majkaNormalizer);
        //Normalizer normalizer = majkaNormalizer;
        //Normalizer normalizer = new NullNormalizer();

        String word;
        while ((word = reader.getNextWord()) != null) {
            logger.debug("Processing word \"" + word + "\"");

            List<NormalizedWord> normWords = normalizer.normalize(word);
            for (NormalizedWord normWord : normWords)
                counter.count(normWord, word);
        }

        logger.info("Cache usage statistics. Request count: {}, hit count: {}, hit ratio: {}%",
                normalizer.getRequestCount(), normalizer.getHitCount(),
                Math.round(100.0 * normalizer.getHitCount() / normalizer.getRequestCount()));

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
        logger.info("WordStats finished");
    }
}
