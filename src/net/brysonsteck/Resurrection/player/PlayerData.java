package net.brysonsteck.Resurrection.player;

import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;

public class PlayerData {
    Hashtable<String, Hashtable<String, String>> playerData = new Hashtable<>();
    String rawData;

    public void saveData(String write) {
        try {
            FileWriter writer = new FileWriter("playerData.resurrection");
            writer.write(write);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readData();
    }

    public void readData() {
        try {
            rawData = "";
            BufferedReader reader = new BufferedReader(new FileReader("playerData.resurrection"));
            String line = "";
            String[] playerData;
            while (true) {
                playerData = new String[3];
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Hashtable<String, Hashtable<String, String>> getPlayers() {
        return playerData;
    }

    public String getRawData() {
        return rawData;
    }
}
