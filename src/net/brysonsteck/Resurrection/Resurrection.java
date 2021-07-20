package net.brysonsteck.Resurrection;

import net.brysonsteck.Resurrection.commands.CommandAbout;
import net.brysonsteck.Resurrection.commands.CommandBug;
import net.brysonsteck.Resurrection.commands.CommandHowLong;
import net.brysonsteck.Resurrection.commands.CommandResurrect;
import net.brysonsteck.Resurrection.player.PlayerListener;
import net.brysonsteck.Resurrection.player.TimeCheck;
import net.brysonsteck.Resurrection.startup.CheckForUpdate;
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

        System.out.println("[Resurrection] Locating the file \"playerData.resurrection\"...");
        // check if playerData.resurrection exists
        File playerFile = new File("plugins/playerData.resurrection");
        if (!playerFile.exists()) {
            System.out.println("[Resurrection] Player data file does not exist. Creating now in the \"plugins\" directory...");
            try {
                playerFile.createNewFile();
                System.out.println("[Resurrection] Player data file created successfully.");
            } catch (IOException e) {
                System.out.println("[Resurrection] An error has occurred creating the player data file!");
                e.printStackTrace();
                System.out.println("[Resurrection] This file is crucial to Resurrection. Since the file could not be created, the plugin will now stop.");
                System.exit(1);
            }
        } else {
            System.out.println("[Resurrection] The player data file has been found!");
        }

        System.out.println("[Resurrection] ---------------------------------------------------------");

        System.out.println("[Resurrection] Registering listeners and adding commands...");
        // register listener
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // register commands
        this.getCommand("about").setExecutor(new CommandAbout(pluginInfo.getVersion(), outdated));
        this.getCommand("bug").setExecutor(new CommandBug());
        this.getCommand("resurrect").setExecutor(new CommandResurrect());
        this.getCommand("howlong").setExecutor(new CommandHowLong());

        System.out.println("[Resurrection] ---------------------------------------------------------");
        System.out.println("[Resurrection] Successfully Started!");
        System.out.println("[Resurrection] ---------------------------------------------------------");

    }

    public static void main(String[] args) {

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

        TimeCheck timeCheck = new TimeCheck((System.currentTimeMillis() + 86212345) - System.currentTimeMillis());
        System.out.println(timeCheck.formatTime());

    }

}
