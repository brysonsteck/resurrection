package net.brysonsteck.Resurrection;

import net.brysonsteck.Resurrection.commands.CommandAbout;
import net.brysonsteck.Resurrection.commands.CommandResurrect;
import net.brysonsteck.Resurrection.startup.CheckForUpdate;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Resurrection extends JavaPlugin {
//    public Plugin plugin = getPlugin(Resurrection.class);

    //spigot things
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        PluginDescriptionFile pluginInfo = getDescription();

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
        this.getCommand("about").setExecutor(new CommandAbout());
        this.getCommand("resurrect").setExecutor(new CommandResurrect());

        System.out.println("[Resurrection] Successfully Started.");
    }

    // end of spigot things
    public static void main(String[] args) {
//        PlayerData playerData = new PlayerData();
//        playerData.saveData("This is the first line\nthis is the second line");
//        System.out.println(playerData.getPlayers());
//        playerData.readData();

//        playerData.saveData("username,false,0");
//        System.out.println("now adding two more lines");
//        playerData.saveData(playerData.getPlayers() + "this is the third line\nthis is the fourth line\nthe thread is now sleeping\nonce more\nand again");
//        System.out.println(playerData.getPlayers());
//        try {
//            Thread.sleep(100000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

}
