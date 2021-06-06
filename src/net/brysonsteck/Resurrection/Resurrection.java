package net.brysonsteck.Resurrection;

import net.brysonsteck.Resurrection.commands.CommandAbout;
import org.bukkit.plugin.Plugin;
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

        // register listener
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // register commands
        this.getCommand("about").setExecutor(new CommandAbout());

        System.out.println("Resurrection: I'm alive!");
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
