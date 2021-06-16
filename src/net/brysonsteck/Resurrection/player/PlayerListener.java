package net.brysonsteck.Resurrection.player;

import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {

    Location spawn;

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        System.out.println("Resurrection: A player has died!");
        Player p = e.getEntity();
        Long timeOfDeath = System.currentTimeMillis();
        Long resurrectionTime = timeOfDeath + 86400000;
//
//        TimeCheck death = new TimeCheck(timeOfDeath);
//        TimeCheck resurrect = new TimeCheck((timeOfDeath + 86400000) - timeOfDeath);
//
//        String deathFormatted = death.formatTime();
//        String resurrectFormatted = resurrect.formatTime();

        p.sendMessage("You have died!! You will be able to respawn in the next 24 hours.");

        new Thread (() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                p.sendMessage("[Resurrection] An error has occurred! Please contact the admin. (Failed to make the thread sleep!)");
            }
            ResurrectPlayer resurrectPlayer = new ResurrectPlayer(p);
        }).start();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        p.setGameMode(GameMode.SPECTATOR);
        spawn = p.getLocation();
        new BukkitRunnable() {
            @Override
            public void run() {
                PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 100, 10, false);
                PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 100, 10, false);
                PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 100, 10, false);
                invisibility.apply(p);
                blindness.apply(p);
                slowness.apply(p);
            }
        }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 1);


    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location location = p.getLocation();
        if (p.getGameMode() == GameMode.SPECTATOR) {
            p.teleport(spawn);
        }
    }
}
