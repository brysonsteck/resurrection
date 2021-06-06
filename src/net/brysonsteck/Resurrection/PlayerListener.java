package net.brysonsteck.Resurrection;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        System.out.println("Resurrection: A player has died!");
        Player p = e.getEntity();
        p.setGameMode(GameMode.SPECTATOR);
        Long timeOfDeath = System.currentTimeMillis();
        Long resurrectionTime = timeOfDeath + 86400000;

        TimeCheck death = new TimeCheck(timeOfDeath);
        TimeCheck resurrect = new TimeCheck(timeOfDeath + 86400000);

        String deathFormatted = death.formatTime();
        String resurrectFormatted = resurrect.formatTime();

        p.sendMessage("You have died!! You will be able to respawn in the next " + resurrectFormatted);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 86400, 255));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location location = p.getLocation();
        if (p.getGameMode() == GameMode.SPECTATOR && p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
            p.teleport(location);
        }
    }
}
