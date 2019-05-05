package wordstats.Configuration;

public interface SettingsUpdater {
    void update(Settings settings) throws ConfigurationException;
}
