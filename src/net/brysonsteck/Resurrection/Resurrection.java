package net.brysonsteck.Resurrection;

import net.brysonsteck.Resurrection.commands.*;
import net.brysonsteck.Resurrection.player.PlayerListener;
import net.brysonsteck.Resurrection.startup.CheckForUpdate;
import net.brysonsteck.Resurrection.startup.ParseSettings;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Resurrection extends JavaPlugin implements Listener {

    //spigot things
    @Override
    public void onDisable() {
        super.onDisable();
        System.out.println("[Resurrection] Resurrection has completed shutdown.");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        System.out.println("[Resurrection] ---------------------------------------------------------");

        System.out.println("[Resurrection] Resurrection is starting!");
        PluginDescriptionFile pluginInfo = getDescription();
        getServer().getPluginManager().registerEvents(this, this);

        if (pluginInfo.getVersion().contains("beta")) {
            // beta message
            System.out.println("[Resurrection] ---------------------------------------------------------");
            System.out.println("[Resurrection]                       WARNING!!!!");
            System.out.println("[Resurrection]      You are running a beta version of Resurrection!");
            System.out.println("[Resurrection] ");
            System.out.println("[Resurrection] This means that this plugin is early in development and");
            System.out.println("[Resurrection] not completely finished, and as a result you may");
            System.out.println("[Resurrection] experience unexpected doodads. Make sure that the plugin");
            System.out.println("[Resurrection] is up-to-date for more features and bug fixes. The plugin");
            System.out.println("[Resurrection] will now check for updates.");
            System.out.println("[Resurrection] ---------------------------------------------------------");
        } else {
            System.out.println("[Resurrection] ---------------------------------------------------------");

        }

        // check for updates
        System.out.println("[Resurrection] Checking for updates...");
        CheckForUpdate check = new CheckForUpdate();
        boolean outdated = false;
        if (check.isSuccess()) {
            String newestVersion = check.getVersion();
            String newestVersionURL = check.getVersionURL();
            if (pluginInfo.getVersion().equals(newestVersion)) {
                System.out.println("[Resurrection] " + newestVersion + " is the latest version of Resurrection.");
            } else {
                System.out.println("[Resurrection] A new version of Resurrection is available! (current: " + pluginInfo.getVersion() + ", newest: " + newestVersion + ")");
                System.out.println("[Resurrection] You can download the latest release on GitHub here \\/");
                System.out.println("[Resurrection] " + newestVersionURL);
                outdated = true;
            }
        }

        System.out.println("[Resurrection] ---------------------------------------------------------");

        ParseSettings parseSettings = null;

        System.out.println("[Resurrection] Locating player data and settings files...");
        // check if playerData.resurrection exists
        File playerFile = new File("plugins/playerData.resurrection");
        File settingsFile = new File("plugins/settings.resurrection");
        if (!playerFile.exists()) {
            System.out.println("[Resurrection] Player data file does not exist. Creating now in the \"plugins\" directory...");
            try {
                playerFile.createNewFile();
                System.out.println("[Resurrection] Player data file created successfully.");
            } catch (IOException e) {
                System.out.println("[Resurrection] An error has occurred creating the player data file!");
                e.printStackTrace();
                System.out.println("[Resurrection] This file is crucial to Resurrection. Since the file could not be created, the plugin will now stop.");
            }
        } else {
            System.out.println("[Resurrection] The player data file has been found!");
        }
        if (!settingsFile.exists()) {
            System.out.println("[Resurrection] Settings file does not exist. (This file is new with the 0.2 beta if you upgraded.) Creating now in the \"plugins\" directory...");
            parseSettings = new ParseSettings();
            System.out.println("[Resurrection] Settings file created successfully.");
        } else {
            System.out.println("[Resurrection] The settings file has also been found!");
        }

        System.out.println("[Resurrection] ---------------------------------------------------------");

        System.out.println("[Resurrection] Registering listeners and adding commands...");
        // register listener
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // register commands
        this.getCommand("about").setExecutor(new CommandAbout(parseSettings.getSetting("debug"), pluginInfo.getVersion(), outdated));
        this.getCommand("bug").setExecutor(new CommandBug(parseSettings.getSetting("debug")));
        this.getCommand("resurrect").setExecutor(new CommandResurrect(parseSettings.getSetting("debug")));
        this.getCommand("howlong").setExecutor(new CommandHowLong(parseSettings.getSetting("debug")));
        this.getCommand("source").setExecutor(new CommandSource(parseSettings.getSetting("debug")));

        System.out.println("[Resurrection] ---------------------------------------------------------");
        System.out.println("[Resurrection] Successfully Started!");
        System.out.println("[Resurrection] ---------------------------------------------------------");

    }

    public static void main(String[] args) {

//        String test = "fals";
//
//        if (!test.toLowerCase().contains("true") && !test.toLowerCase().contains("false")) {
//            System.out.println("fail");
//        }

        // DO THIS
//        PlayerData playerData = new PlayerData();
//        System.out.println("--- Reading Player data file ---");
//        playerData.readData();
//        System.out.println(playerData.getPlayers());
//        System.out.println(playerData.getRawData());
//        System.out.println("--- Oh look! A new player joined. Adding them. ---");
//        String rawData = playerData.getRawData();
//        rawData = rawData + ";bryzinga,false,0";
//        playerData.saveData(rawData);
//        System.out.println(playerData.getPlayers());
//        System.out.println(playerData.getRawData());
//        System.out.println("--- A player has died! Update the data file! ---");
//        rawData = playerData.getRawData();
//        String[] rawPlayers = rawData.split(";");
//        String[] rawSinglePlayer = new String[3];
//        int index = 0;
//        for (String players : rawPlayers) {
//            if (players.startsWith("bryzinga")) {
//                String[] playerSplit = players.split(",");
//                playerSplit[1] = "true";
//                playerSplit[2] = "12345";
//
//                rawPlayers[index] = String.join(",", playerSplit);
//                break;
//
//            }
//            index++;
//        }
//        rawData = String.join(";", rawPlayers);
//        playerData.saveData(rawData);
//        System.out.println(rawData);
//        String[] array = ";bryzinga,false,0".split(";");
//        System.out.println(array.length);

//        TimeCheck timeCheck = new TimeCheck((System.currentTimeMillis() + 86212345) - System.currentTimeMillis());
//        System.out.println(timeCheck.formatTime());
//        System.out.println(System.currentTimeMillis());
    }

}
