package wordstats;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class WordReaderTest {
    @Parameterized.Parameter(0)
    public String text;

    @Parameterized.Parameter(1)
    public String[] expectedWords;

    @Parameterized.Parameter(2)
    public String description;

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {
                    "",
                    new String[] {},
                    "Empty text"
                },
                {
                    "happy",
                    new String[] {"happy"},
                    "One word"
                },
                {
                    "\"Well, what’s the matter with you?\"",
                    new String[] {"Well", "what", "s", "the", "matter", "with", "you"},
                    "One line"
                },
                {
                    "He himself — my brother-in-law — came back by train.",
                    new String[] {"He", "himself", "my", "brother-in-law", "came", "back", "by", "train"},
                    "Hyphen in the middle of a line"
                },
                {
                   "One\r\nTwo\r\nThree",
                   new String[] {"One", "Two", "Three"},
                   "Multiline (Windows)"
                },
                {
                    "One\nTwo\nThree",
                    new String[] {"One", "Two", "Three"},
                    "Multiline (Unix)"
                },
                {
                    "One\rTwo\rThree",
                    new String[] {"One", "Two", "Three"},
                    "Multiline (MacOS)"
                },
                {
                    "\n\n\n\n",
                    new String[] {},
                    "Empty lines"
                },
                {
                    "\n\n\n\n",
                    new String[] {},
                    "Empty lines"
                },
                {
                    "Eins-\nZwei-\nDrei-\nVier",
                    new String[] {"EinsZweiDreiVier"},
                    "Hyphen at the end of a line"
                },
                {
                    "Eins-",
                    new String[] {"Eins"},
                    "Hyphen at the end of a text"
                },
                {
                    "п'ять шість сім",
                    new String[]{"п'ять", "шість", "сім"},
                    "Apostrophe"
                },
                {
                    "weiß schön über",
                    new String[] {"weiß", "schön", "über"},
                    "German letters"
                }
        });
    }

    @Test
    public void testWordReader() throws Exception {
        try (InputStreamReader stream = toInputStream(text)) {
            WordReader reader = new WordReader(stream);
            String word;
            int wordCount = 0;
            while((word = reader.getNextWord()) != null)
                assertEquals("Wrong word. Case: " + description + ": ", expectedWords[wordCount++], word);
            assertEquals("Wrong word count. Case: " + description + ": ", expectedWords.length, wordCount);
        }
    }

    private InputStreamReader toInputStream(String s) {
        return new InputStreamReader(
                new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
    }
}
