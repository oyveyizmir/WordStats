package wordstats;

public class CommandLineParser {
    public Configuration ParseArgs(String[] args) {
        Configuration conf = new Configuration();

        conf.language = Language.German;

        return conf;
    }
}
