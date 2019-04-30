package wordstats.Normalization;

import wordstats.Language;
import wordstats.PartOfSpeech;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
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
        System.out.println(FileSystems.getDefault().getPath(".").toAbsolutePath().toString()); //TODO: remove
        process = Runtime.getRuntime().exec("C:\\opt\\majka\\majka.exe -f C:\\opt\\majka\\" + dictionary);
        stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    @Override
    public List<NormalizedWord> normalize(String word) throws Exception {
        stdin.write(word);
        stdin.newLine();
        stdin.flush();

        ArrayList<NormalizedWord> result = new ArrayList<>();
        String line;
        while(stdout.ready() && (line = stdout.readLine()) != null) {
            Matcher matcher = outputPattern.matcher(line);
            if (!matcher.find()) {
                System.out.println("Cannot parse word: " + word + ", " + line);
                continue;
                //throw new Exception("Cannot parse word: " + word + ", " + line);
            }
            String normalizedWord = matcher.group(1);
            try {
                PartOfSpeech partOfSpeech = toPartOfSpeech(matcher.group(3));
                if (partOfSpeech == PartOfSpeech.PresentParticiple || partOfSpeech == PartOfSpeech.PastParticiple)
                    continue;
                result.add(new NormalizedWord(normalizedWord, partOfSpeech, line));
            }
            catch(Exception e)
            {
                throw new Exception("Error while processing " + line, e);
            }
        }
        return result;
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
                return PartOfSpeech.Intejection;
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
