package net.brysonsteck.Resurrection;

import net.brysonsteck.Resurrection.commands.CommandAbout;
import net.brysonsteck.Resurrection.commands.CommandResurrect;
import net.brysonsteck.Resurrection.player.PlayerData;
import net.brysonsteck.Resurrection.player.PlayerListener;
import net.brysonsteck.Resurrection.startup.CheckForUpdate;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Hashtable;

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
        }

        // check for updates
        System.out.println("[Resurrection] Checking for updates...");
        CheckForUpdate check = new CheckForUpdate();
        String newestVersion = check.getVersion();
        String newestVersionURL = check.getVersionURL();
        if (pluginInfo.getVersion().equals(newestVersion)) {
            System.out.println("[Resurrection] " + newestVersion + " is the latest version of Resurrection.");
        } else {
            System.out.println("[Resurrection] A new version of Resurrection is available! (current: " + pluginInfo.getVersion() + ", newest: " + newestVersion);
            System.out.println("[Resurrection] You can download the latest release on GitHub here \\/");
            System.out.println("[Resurrection] " + newestVersionURL);
        }

        // register listener
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // register commands
        System.out.println("[Resurrection] Adding commands...");
        this.getCommand("about").setExecutor(new CommandAbout());
        this.getCommand("resurrect").setExecutor(new CommandResurrect());

        System.out.println("[Resurrection] Successfully Started!");
    }

    public static void main(String[] args) {

        // DO THISgit 
        PlayerData playerData = new PlayerData();
        System.out.println("--- Reading Player data file ---");
        playerData.readData();
        System.out.println(playerData.getPlayers());
        System.out.println(playerData.getRawData());
        System.out.println("--- Oh look! A new player joined. Adding them. ---");
        playerData.saveData("bryzinga,false,0");
        System.out.println(playerData.getPlayers());
        System.out.println(playerData.getRawData());
        System.out.println("--- A player has died! Update the data file! ---");
        String rawData = playerData.getRawData();
        String[] rawPlayers = rawData.split(";");
        String[] rawSinglePlayer = new String[3];
        int index = 0;
        for (String players : rawPlayers) {
            if (players.startsWith("bryzinga")) {
                String[] playerSplit = players.split(",");
                playerSplit[1] = "true";
                playerSplit[2] = "12345";

                rawPlayers[index] = String.join(",", playerSplit);
                break;

            }
            index++;
        }
        rawData = String.join(";", rawPlayers);
        System.out.println(rawData);

    }

}
