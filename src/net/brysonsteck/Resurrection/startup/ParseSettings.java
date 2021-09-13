package net.brysonsteck.Resurrection.startup;

import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
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
            if (!verifySettings()) {
                System.out.println("[Resurrection] There is a syntax issue inside the Settings file:");
                if (!settingsComplete) {
                    System.out.println("[Resurrection]     The setting \"" + failedSetting + "\" is not present in the settings file.\n" +
                            "[Resurrection]     Please double check the settings file to make sure the setting exists and a valid corresponding value is set.\n" +
                            "[Resurrection]     Example: \"resurrection_time=86400000\"\n" +
                            "[Resurrection]     Example: \"debug=false\"");
                } else if (!valuesComplete) {
                    System.out.println("[Resurrection]     The setting \"" + failedSetting + "\" contains an invalid or empty value.\n" +
                            "[Resurrection]     Please double check the settings file to make sure that a valid value is set for this setting.\n" +
                            "[Resurrection]     Example: \"resurrection_time=86400000\"\n" +
                            "[Resurrection]     Example: \"debug=false\"");
                }
                System.out.println("[Resurrection] This file is crucial to Resurrection. Since the file is not complete, the plugin will now stop.");
                Bukkit.getPluginManager().disablePlugin(JavaPlugin.getProvidingPlugin(Resurrection.class));
            }
        } catch (IOException e) {
            System.out.println("[Resurrection] There was an issue reading the Settings file:");
            e.printStackTrace();
            System.out.println("[Resurrection] This file is crucial to Resurrection. Since the file is not complete, the plugin will now stop.");
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
