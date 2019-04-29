package wordstats;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MajkaNormalizer implements Normalizer {
    private Language language;
    private Process process;
    private BufferedWriter stdin;
    private BufferedReader stdout;
    private Pattern outputPattern = Pattern.compile("(\\p{L}+):(\\p{L}+)");

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
        process = Runtime.getRuntime().exec("C:\\opt\\majka\\majka.exe -f C:\\opt\\majka\\" + dictionary);
        stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    @Override
    public List<NormalizationResult> normalize(String word) throws Exception {
        stdin.write(word);
        stdin.newLine();
        stdin.flush();

        ArrayList<NormalizationResult> result = new ArrayList<>();
        String line;
        while(stdout.ready() && (line = stdout.readLine()) != null) {
            Matcher matcher = outputPattern.matcher(line);
            if (!matcher.find())
                throw new Exception("Cannot parse " + line);
            String normalizedWord = matcher.group(1);
            PartOfSpeech partOfSpeech = toPartOfSpeech(matcher.group(2));

            result.add(new NormalizationResult(normalizedWord, partOfSpeech, line));
        }
        return result;
    }

    private PartOfSpeech toPartOfSpeech(String str) throws Exception {
        switch(str) {
            case "SUB":
                return PartOfSpeech.Noun;
            case "VER":
                return PartOfSpeech.Verb;
            default:
                throw new Exception("Unknown part of speech " + str);
        }
    }
}
