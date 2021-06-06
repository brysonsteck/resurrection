package net.brysonsteck.Resurrection;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin() {

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        System.out.println("Resurrection: A player has died!");
        Player p = e.getEntity();
        p.setGameMode(GameMode.SPECTATOR);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000000000, 500));
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000000, 500));
        Long timeOfDeath = System.currentTimeMillis();
        Long resurrectionTime = timeOfDeath + 86400000;

        TimeCheck death = new TimeCheck(timeOfDeath);
        TimeCheck resurrect = new TimeCheck(timeOfDeath + 86400000);

        String deathFormatted = death.formatTime();
        String resurrectFormatted = resurrect.formatTime();


        p.sendMessage("You have died! You will be able to respawn in 24 hours.");
    }
}
