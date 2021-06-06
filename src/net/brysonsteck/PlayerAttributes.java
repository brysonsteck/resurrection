package net.brysonsteck;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class PlayerAttributes implements Listener {
    public Plugin plugin = Resurrection.getPlugin(Resurrection.class);

    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        Player k = event.getEntity().getKiller();
        int killcount = plugin.getConfig().getInt("Users." + k.getUniqueId().toString() + ".Kills") ;
        int deathcount = plugin.getConfig().getInt("Users." + p.getUniqueId().toString() + ".Deaths") ;

        plugin.getConfig().set("Users." + p.getUniqueId().toString() + ".Deaths", deathcount + 1);
        plugin.getConfig().set("Users." + k.getUniqueId().toString() + ".Kills", killcount + 1);
        plugin.saveConfig();


    }
}