package net.brysonsteck.Resurrection.player;

import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.Bukkit;
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
//
//        TimeCheck death = new TimeCheck(timeOfDeath);
//        TimeCheck resurrect = new TimeCheck((timeOfDeath + 86400000) - timeOfDeath);
//
//        String deathFormatted = death.formatTime();
//        String resurrectFormatted = resurrect.formatTime();

        p.sendMessage("You have died!! You will be able to respawn in the next 24 hours.");

        new BukkitRunnable() {
            // save death information to player file
            @Override
            public void run() {
                for (PotionEffect effect : p.getActivePotionEffects())
                    p.removePotionEffect(effect.getType());
                p.setGameMode(GameMode.SURVIVAL);
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + p.getDisplayName() + " has resurrected!");
            }
        }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 1728000);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        p.setGameMode(GameMode.SPECTATOR);
        spawn = p.getLocation();
        new BukkitRunnable() {
            @Override
            public void run() {
//                PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 1728000, 10, false);
                PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 1728000, 10, false);
                PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 1728000, 10, false);
//                invisibility.apply(p);
                blindness.apply(p);
                slowness.apply(p);
            }
        }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 1);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.SPECTATOR) {
            p.teleport(spawn);
        }
    }
}
