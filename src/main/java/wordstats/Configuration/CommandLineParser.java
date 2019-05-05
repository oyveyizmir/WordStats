package wordstats.Configuration;

public class CommandLineParser implements SettingsUpdater {
    private String[] args;

    public CommandLineParser(String[] args) {
        this.args = args;
    }

    @Override
    public void update(Settings settings) {
        if (args.length >= 1)
            settings.inputFile = args[0];
        if (args.length >= 2)
            settings.outputFile = args[1];
        if (args.length >= 3)
            settings.errorFile = args[2];
    }
}
