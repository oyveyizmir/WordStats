package wordstats.Configuration;

public class SettingsProvider {
    private Settings settings = new Settings();
    private SettingsUpdater[] settingsUpdaters;

    public SettingsProvider(SettingsUpdater... settingsUpdaters) {
        this.settingsUpdaters = settingsUpdaters;
    }

    public Settings getSettings() { return settings; }

    public void readSettings() throws ConfigurationException {
        for (SettingsUpdater updater : settingsUpdaters)
            updater.update(settings);
    }
}
