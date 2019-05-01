package wordstats.Normalization;

import wordstats.Language;
import wordstats.PartOfSpeech;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MajkaNormalizer implements Normalizer {
    private Language language;
    private Process process;
    private BufferedWriter stdin;
    private BufferedReader stdout;
    private Pattern outputPattern = Pattern.compile("((\\p{L}+)|\\?):(\\p{L}+\\d*)");

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
        stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));

        System.err.println("Initializing");

        stdin.write("ja");
        stdin.newLine();
        stdin.flush();

        System.err.print("Waiting");
        for (int i = 0; !stdout.ready() && i < 60; i++) {
            Thread.sleep(1000);
            System.err.print(".");
        }
        System.err.println();

        if (stdout.ready()) {
            String line = stdout.readLine();
            if (!"ja:ADV.MOD".equals(line))
                throw new Exception("Majka initialization failed. Unexpected output: " + line);
        }

        System.err.println("Initialized");
    }

    @Override
    public List<NormalizedWord> normalize(String word) throws Exception {
        System.err.println("NORMALIZING " + word);
        stdin.write(word);
        stdin.newLine();
        stdin.flush();

        Map<String, NormalizedWord> normWords = new HashMap<>();
        String line;

        for (int i = 0; !stdout.ready() && i < 10; i++) {
            Thread.sleep(100);
        }

        if (!stdout.ready())
            System.err.println("No response for " + word);

        while (stdout.ready() && (line = stdout.readLine()) != null) {
            System.err.println("READ " + line);
            Matcher matcher = outputPattern.matcher(line);
            if (!matcher.find()) {
                System.err.println("Cannot parse word: " + word + ", " + line);
                continue;
                //throw new Exception("Cannot parse word: " + word + ", " + line);
            }
            String normalizedWord = matcher.group(1);
            try {
                PartOfSpeech partOfSpeech = toPartOfSpeech(matcher.group(3));
                if (partOfSpeech == PartOfSpeech.PresentParticiple || partOfSpeech == PartOfSpeech.PastParticiple)
                    continue; //TODO: only for German
                NormalizedWord normWord = getNormalizedWord(normWords, normalizedWord, partOfSpeech);
                normWord.addMorphDetail(line);
            }
            catch(Exception e)
            {
                throw new Exception("Error while processing " + line, e);
            }
            Thread.sleep(100);
        }

        return new ArrayList<>(normWords.values());
    }

    private static NormalizedWord getNormalizedWord(Map<String, NormalizedWord> normWords,
                                             String word, PartOfSpeech partOfSpeech) {
        //NormalizedWord normWord = normWords.computeIfAbsent(normalizedWord + ":" + partOfSpeech,
        //        k -> new NormalizedWord(normalizedWord, partOfSpeech));
        String key = word + ":" + partOfSpeech;
        NormalizedWord normWord = normWords.get(key);
        if (normWord == null) {
            normWord = new NormalizedWord(word, partOfSpeech);
            normWords.put(key, normWord);
        }
        return normWord;
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
