package wordstats;

import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws Exception {
        WordReader reader = new WordReader();
        WordCounter counter = new WordCounter();
        String word;


        while ((word = reader.getNextWord()) != null)
            counter.count(word);

        counter.sort();
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        for(WordEntry entry : counter.getWords()) {
            out.println(entry.getWord() + ", " + entry.getCount());
            //TODO: add variations
        }

    }
}
