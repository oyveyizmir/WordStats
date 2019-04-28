package wordstats;

import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws Exception {
        String word;
        WordReader reader = new WordReader(System.in);
        WordCounter counter = new WordCounter();

        while ((word = reader.getNextWord()) != null)
            counter.count(word);

        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        for (WordEntry entry : counter.getWords()) {
            out.println(entry.getWord() + ", " + entry.getCount());
            //TODO: add variations
        }

    }
}
