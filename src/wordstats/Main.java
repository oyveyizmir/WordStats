package wordstats;

import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws Exception {
        WordReader reader = new WordReader();
        String word;

        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        while ((word = reader.getNextWord()) != null) {
            out.println(word);
        }
    }
}
