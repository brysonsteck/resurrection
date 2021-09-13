package net.brysonsteck.Resurrection.player;


import net.brysonsteck.Resurrection.Resurrection;
import net.brysonsteck.Resurrection.startup.ParseSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Hashtable;

public class PlayerData {
    Hashtable<String, Hashtable<String, String>> playerData = new Hashtable<>();
    String rawData;
    boolean DEBUG = Boolean.parseBoolean(new ParseSettings()
            .getSetting("debug"));

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
            System.out.println("[Resurrection] There was an issue saving the player data file.");
            e.printStackTrace();
            System.out.println("[Resurrection] Resurrection will continue to run despite this error, but avoid shutting down the server until a successful save occurs.");
            System.out.println("[Resurrection] In the mean time, check to make sure the playerData file exists and you have permissions to write to it.");
        }
        readData();

        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Player data saved successfully");
        }
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
                    TimeCheck timeCheck = new TimeCheck(Long.parseLong(playerData[2]));
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: player: " + playerData[0] + " | dead: " + playerData[1] + " | ms to resurrect at: " + playerData[2]);
                }
            }
        } catch (IOException e) {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Error occurred while trying to read player data. Resurrection is shutting down");
            }

            System.out.println("[Resurrection] There was an issue reading the player data file.");
            e.printStackTrace();
            System.out.println("[Resurrection] This file is crucial to Resurrection. Since the file could not be read, the plugin will now stop.");
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
