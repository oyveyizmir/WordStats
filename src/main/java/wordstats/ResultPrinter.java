package wordstats;

import wordstats.Counter.WordCounter;
import wordstats.Counter.WordEntry;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class ResultPrinter {
    public ResultPrinter(String outputFile) {
    }

    public void print(WordCounter counter) throws UnsupportedEncodingException {
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
    }
}
