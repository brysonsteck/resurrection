package net.brysonsteck.Resurrection.player;


import net.brysonsteck.Resurrection.Resurrection;
import net.brysonsteck.Resurrection.startup.ParseSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Hashtable;
import java.util.logging.Logger;

public class PlayerData {
    Hashtable<String, Hashtable<String, String>> playerData = new Hashtable<>();
    String rawData;
    boolean DEBUG = Boolean.parseBoolean(new ParseSettings()
            .getSetting("debug"));
    Logger log = JavaPlugin.getProvidingPlugin(Resurrection.class).getLogger();

    public void saveData(String write) {
        try {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Attempting to save player data");
            }
            FileWriter writer = new FileWriter("plugins/playerData.resurrection");
            writer.write(write);
            writer.close();
        } catch (IOException e) {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Error occurred while trying to save player data, avoid shutting down the server");
            }
            log.warning("There was an issue saving the player data file.");
            e.printStackTrace();
            log.warning("Resurrection will continue to run despite this error, but avoid shutting down the server until a successful save occurs.");
            log.warning("In the mean time, check to make sure the playerData file exists and you have permissions to write to it.");
        }
        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Player data saved successfully, rereading data to ensure Resurrection is up to date");
        }

        readData();
    }

    public void readData() {
        try {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Attempting to read player data");
            }
            rawData = "";
            BufferedReader reader = new BufferedReader(new FileReader("plugins/playerData.resurrection"));
            String line;
            String[] playerData;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                rawData = rawData + line;
                playerData = line.split(",");
                Hashtable<String, String> playerHash = new Hashtable<>();
                playerHash.put("dead", playerData[1]);
                playerHash.put("timeLeft", playerData[2]);
                this.playerData.put(playerData[0], playerHash);
                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: player: " + playerData[0].replaceFirst(";","") + " | dead: " + playerData[1] + " | ms to resurrect at: " + playerData[2]);
                }
            }
            reader.close();
        } catch (IOException e) {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Error occurred while trying to read player data. Resurrection is shutting down");
            }

            log.severe("There was an issue reading the player data file.");
            e.printStackTrace();
            log.severe("This file is crucial to Resurrection. Since the file could not be read, the plugin will now stop.");
            Bukkit.getPluginManager().disablePlugin(JavaPlugin.getProvidingPlugin(Resurrection.class));
        }
        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Player data read successfully");
        }
    }

//    public Hashtable<String, Hashtable<String, String>> getPlayers() {
//        return playerData;
//    }

    public String getRawData() {
        return rawData;
    }
}
