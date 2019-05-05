package wordstats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wordstats.Configuration.*;
import wordstats.Counter.WordCounter;
import wordstats.Counter.WordEntry;
import wordstats.Normalization.*;

import java.io.PrintStream;
import java.util.List;

public class Main {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        logger.info("Starting WordStats");

        SettingsProvider settingsProvider = new SettingsProvider(
                new ConfigFileReader(), new CommandLineParser(args));
        try {
            settingsProvider.readSettings();
        } catch (ConfigurationException e) {
            logger.info(e.getMessage());
            System.err.println(e.getMessage());
            logger.info("WordStats finished");
            return;
        }

        Settings settings = settingsProvider.getSettings();

        try (WordReader reader = new WordReader(settings.inputFile)) {
            WordCounter counter = new WordCounter();
            MajkaNormalizer majkaNormalizer = new MajkaNormalizer(settings.language);
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

            ResultPrinter printer = new ResultPrinter(settings.outputFile);
            printer.print(counter);
        }

        logger.info("WordStats finished");
    }
}
