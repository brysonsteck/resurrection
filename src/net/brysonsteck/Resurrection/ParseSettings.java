package net.brysonsteck.Resurrection;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;

public class ParseSettings {
    Hashtable<String, String> settings = new Hashtable<>();

    // <setting that failed / does it exist? (true = the value is wrong, false = the setting is missing)>
    String failedSetting;
    boolean settingsComplete;
    boolean valuesComplete;


    public ParseSettings() {
        try {
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
            if (!verifySettings()) {
                System.out.println("[Resurrection] There is a syntax issue inside the Settings file:");
                if (!settingsComplete) {
                    System.out.println("[Resurrection]     The setting \"" + failedSetting + "\" is not present in the settings file.\n" +
                            "[Resurrection]     Please double check the settings file to make sure the setting exists and a valid corresponding value is set.\n" +
                            "[Resurrection]     Example: \"resurrection_time=8640000\"\n" +
                            "[Resurrection]     Example: \"debug=false\"");
                } else if (!valuesComplete) {
                    System.out.println("[Resurrection]     The setting \"" + failedSetting + "\" contains an invalid or empty value.\n" +
                            "[Resurrection]     Please double check the settings file to make sure that a valid value is set for this setting.\n" +
                            "[Resurrection]     Example: \"resurrection_time=8640000\"\n" +
                            "[Resurrection]     Example: \"debug=false\"");
                }
                System.out.println("[Resurrection] This file is crucial to Resurrection. Since the file is not complete, the plugin will now stop.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("[Resurrection] There was an issue reading the Settings file.");
            e.printStackTrace();
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
            long time = Long.parseLong(settings.get("resurrection_time"));
        } catch (NumberFormatException | NullPointerException e) {
            failedSetting = "resurrection_time";
            return false;
        }

        // is debug a boolean?
        if (settings.get("debug") == null) {
            failedSetting = "debug";
            return false;
        }
        if (settings.get("debug").toLowerCase().contains("true") && settings.get("debug").toLowerCase().contains("false")) {
            failedSetting = "debug";
            return false;
        }
        valuesComplete = true;

        return true;
    }

    public String getSetting(String setting) {
        return settings.get(setting);
    }
}
