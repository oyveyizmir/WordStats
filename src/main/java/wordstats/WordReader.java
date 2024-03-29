package wordstats;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordReader implements Closeable {
    private enum State {
        LineRequired,
        WordRequired,
        EndOfInput
    }

    private Scanner scanner;
    private Pattern wordPattern = Pattern.compile("\\b\\p{L}[-'\\p{L}+]*\\b");
    private Matcher matcher;
    private State state = State.LineRequired;

    public WordReader(String inputFile) throws FileNotFoundException {
        if (inputFile == null)
            scanner = new Scanner(System.in);
        else
            scanner = new Scanner(new FileReader(inputFile));
    }

    public WordReader(InputStreamReader streamReader) {
        scanner = new Scanner(streamReader);
    }

    public String getNextWord() throws Exception {
        switch (state) {
            case LineRequired:
                if (scanner.hasNextLine()) {
                    String line = readLine();
                    matcher = wordPattern.matcher(line);
                    return getNextWord(State.WordRequired);
                }
                else
                    return getNextWord(State.EndOfInput);

            case WordRequired:
                if (matcher.find())
                    return matcher.group();
                else
                    return getNextWord(State.LineRequired);

            case EndOfInput:
                return null;

            default:
                throw new Exception("Invalid state " + state);
        }
    }

    private String getNextWord(State newState) throws Exception {
        state = newState;
        return getNextWord();
    }

    private String readLine() {
        if (!scanner.hasNextLine())
            return "";

        String line = scanner.nextLine().trim();
        if (!line.endsWith("-"))
            return line;

        return line.substring(0, line.length() - 1) + readLine();
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
