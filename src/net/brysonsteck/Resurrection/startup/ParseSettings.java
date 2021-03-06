package net.brysonsteck.Resurrection.startup;

import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Hashtable;
import java.util.logging.Logger;

public class ParseSettings {
    Hashtable<String, String> settings = new Hashtable<>();
    // <setting that failed / does it exist? (true = the value is wrong, false = the setting is missing)>
    String failedSetting;
    boolean settingsComplete;
    boolean valuesComplete;

    public ParseSettings() {
        Logger log = JavaPlugin.getProvidingPlugin(Resurrection.class).getLogger();
        try {
            File settingsFile = new File("plugins/settings.resurrection");
            if (!settingsFile.exists()) {
                // create default settings file
                FileWriter writer = new FileWriter(settingsFile);
                writer.write("# This is the default settings file. All lines starting with a '#' are treated as comments and will be ignored.\n" +
                        "# 'resurrection_time' is the amount of time in milliseconds Resurrection will force the player to wait. Default value is 8640000 milliseconds (24 hours).\n" +
                        "resurrection_time=86400000\n" +
                        "# 'debug' enables debug messages in the console and players' chat as the plugin runs. The only valid values are 'true' and 'false'. Default value is false.\n" +
                        "debug=false");
                writer.close();
            }
            String rawData = "";
            BufferedReader reader = new BufferedReader(new FileReader("plugins/settings.resurrection"));
            String line;
            String[] setting;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                } else if (!line.startsWith("#")) {
                    rawData = rawData + line;
                    setting = line.split("=");
                    settings.put(setting[0], setting[1]);
                }
            }
            reader.close();
            if (!verifySettings()) {
                log.severe("There is a syntax issue inside the Settings file:");
                if (!settingsComplete) {
                    log.severe("    The setting \"" + failedSetting + "\" is not present in the settings file.");
                    log.severe("    Please double check the settings file to make sure the setting exists and a valid corresponding value is set.");
                    log.severe("    Example: \"resurrection_time=86400000\"");
                    log.severe("    Example: \"debug=false\"");
                } else if (!valuesComplete) {
                    log.severe("The setting \"" + failedSetting + "\" contains an invalid or empty value.");
                    log.severe("    Please double check the settings file to make sure that a valid value is set for this setting.");
                    log.severe("    Example: \"resurrection_time=86400000\"");
                    log.severe("    Example: \"debug=false\"");
                }
                log.severe("This file is crucial to Resurrection. Since the file is not complete, the plugin will now stop.");
                Bukkit.getPluginManager().disablePlugin(JavaPlugin.getProvidingPlugin(Resurrection.class));
            }
        } catch (IOException e) {
            log.severe("There was an issue reading the Settings file:");
            e.printStackTrace();
            log.severe("This file is crucial to Resurrection. Since the file is not complete, the plugin will now stop.");
            Bukkit.getPluginManager().disablePlugin(JavaPlugin.getProvidingPlugin(Resurrection.class));
        }
    }

    public boolean verifySettings() {
        settingsComplete = false;
        valuesComplete = false;

        if (!settings.containsKey("resurrection_time")) {
            failedSetting = "resurrection_time";
            return false;
        } else if (!settings.containsKey("debug")) {
            failedSetting = "debug";
            return false;
        }
        settingsComplete = true;

        // is resurrection_time a long?
        try {
            Long.parseLong(settings.get("resurrection_time"));
        } catch (NumberFormatException | NullPointerException e) {
            failedSetting = "resurrection_time";
            return false;
        }

        // is debug a boolean?
        if (settings.get("debug") == null) {
            failedSetting = "debug";
            return false;
        }
        if (!settings.get("debug").toLowerCase().contains("true") && !settings.get("debug").toLowerCase().contains("false")) {
            failedSetting = "debug";
            return false;
        }
        valuesComplete = true;

        return true;
    }

    public String getSetting(String setting) {
        return settings.get(setting);
    }

    public boolean isSettingsComplete() { return settingsComplete && valuesComplete; }
}
