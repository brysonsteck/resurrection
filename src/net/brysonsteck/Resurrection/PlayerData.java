package net.brysonsteck.Resurrection;

import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;

public class PlayerData {
    Hashtable<String, Hashtable<String, String>> playerData = new Hashtable<>();

    public void saveData(String write) {
        try {
            FileWriter writer = new FileWriter("data/player.data");
            writer.write(write);
            writer.close();
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/player.data"));
            String line = "";
            String[] playerData;
            while (true) {
                playerData = new String[3];
                line = reader.readLine();
                if (line == null) {
                    break;
                }
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
}
