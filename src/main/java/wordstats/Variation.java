package wordstats;

public class Variation {
    private String word;
    private int count;

    public Variation(String word) {
        this.word = word;
        count = 1;
    }

    public String getWord() { return word; }

    public int getCount() { return count; }
}
