package net.brysonsteck;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        System.out.println("Resurrection: A player has died!");
        Player p = e.getEntity();
        Long timeOfDeath = System.currentTimeMillis();
        Long resurrectionTime = timeOfDeath + 86400000;

        p.sendMessage("You have died at " + timeOfDeath + ". You will respawn at " + resurrectionTime);
    }
}
