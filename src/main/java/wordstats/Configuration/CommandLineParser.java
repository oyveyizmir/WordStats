package wordstats.Configuration;

import wordstats.Language;

public class CommandLineParser {
    public Settings ParseArgs(String[] args) {
        Settings conf = new Settings();

        conf.language = Language.German;

        return conf;
    }
}
