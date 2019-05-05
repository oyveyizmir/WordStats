package wordstats.Normalization;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import wordstats.Language;
import wordstats.PartOfSpeech;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MajkaNormalizer implements Normalizer {
    private static Logger logger = LogManager.getLogger();

    private Language language;
    private Process process;
    private BufferedWriter stdin;
    private BufferedReader stdout;
    private Pattern outputPattern = Pattern.compile("(.+):(\\p{L}+\\d*)");

    public MajkaNormalizer(Language language) {
        this.language = language;
    }

    private static String getDictionary(Language language) throws Exception {
        switch(language) {
            case English:
                return "w-lt.en.fsa";
            case German:
                return "w-lt.ger.fsa";
            default:
                throw new Exception("Language " + language + " is not supported.");
        }
    }

    public void initialize() throws Exception {
        String dictionary = getDictionary(language);
        //System.out.println(FileSystems.getDefault().getPath(".").toAbsolutePath().toString()); //TODO: remove
        process = Runtime.getRuntime().exec("C:\\opt\\majka\\majka.exe -f C:\\opt\\majka\\" + dictionary);
        stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8));
        stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

        logger.debug("Initializing");

        stdin.write("ja");
        stdin.newLine();
        stdin.flush();

        logger.debug("Waiting for response from majka");
        for (int i = 0; !stdout.ready() && i < 60; i++)
            Thread.sleep(1000);

        if (stdout.ready()) {
            String line = stdout.readLine();
            if (!"ja:ADV.MOD".equals(line))
                throw new Exception("Majka initialization failed. Unexpected output: " + line);
        }

        logger.debug("Initialized");
    }

    @Override
    public List<NormalizedWord> normalize(String word) throws Exception {
        logger.debug("Normalizing \"" + word +"\"");

        stdin.write(word);
        stdin.newLine();
        stdin.flush();

        Map<String, NormalizedWord> normWords = new HashMap<>();
        String line;

        for (int i = 0; !stdout.ready() && i < 10; i++) {
            Thread.sleep(10);
        }

        if (stdout.ready()) {
            while (stdout.ready() && (line = stdout.readLine()) != null) {
                logger.debug("Read response \"" + line + "\"");
                Matcher matcher = outputPattern.matcher(line);
                if (!matcher.find()) {
                    String message = "Cannot parse response for word " + word + ": " + line;
                    logger.info(message);
                    System.err.println(message);
                    continue;
                    //throw new Exception("Cannot parse word: " + word + ", " + line);
                }
                String normalizedWord = dropSpecialChars(matcher.group(1));
                try {
                    PartOfSpeech partOfSpeech = toPartOfSpeech(matcher.group(2));
                    if (partOfSpeech == PartOfSpeech.PresentParticiple || partOfSpeech == PartOfSpeech.PastParticiple)
                        continue; //TODO: only for German
                    NormalizedWord normWord = normWords.computeIfAbsent(
                            normalizedWord + ":" + partOfSpeech,
                            k -> new NormalizedWord(normalizedWord, partOfSpeech));
                    normWord.addMorphDetail(line);
                } catch (Exception e) {
                    throw new Exception("Error while processing " + line, e);
                }
                Thread.sleep(10);
            }
        } else {
            String message = "No response for word \"" + word + "\"";
            logger.info(message);
            System.err.println(message);
        }

        return new ArrayList<>(normWords.values());
    }

    private static String dropSpecialChars(String str) {
        return str.chars()
                .filter(c -> "[]()".indexOf(c) < 0)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static PartOfSpeech toPartOfSpeech(String str) throws Exception {
        switch(str) {
            case "ABK":
                return PartOfSpeech.Abbreviation;
            case "ADJ":
                return PartOfSpeech.Adjective;
            case "ADV":
                return PartOfSpeech.Adverb;
            case "ART":
                return PartOfSpeech.Article;
            case "EIG":
                return PartOfSpeech.ProperNoun;
            case "INJ":
                return PartOfSpeech.Interjection;
            case "KON":
                return PartOfSpeech.Conjunction;
            case "NEG":
                return PartOfSpeech.Negation;
            case "PA1":
                return PartOfSpeech.PresentParticiple;
            case "PA2":
                return PartOfSpeech.PastParticiple;
            case "PRO":
                return PartOfSpeech.Pronoun;
            case "PRP":
                return PartOfSpeech.Preposition;
            case "SUB":
                return PartOfSpeech.Noun;
            case "ZAL":
                return PartOfSpeech.Numeral;
            case "VER":
                return PartOfSpeech.Verb;
            case "ZUS":
                return PartOfSpeech.ZUS; //TODO: what is it?
            default:
                return PartOfSpeech.ZUS;
                //throw new Exception("Unknown part of speech " + str);
        }
    }
}
