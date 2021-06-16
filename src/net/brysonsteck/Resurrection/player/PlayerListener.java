package net.brysonsteck.Resurrection.player;

import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.*;
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

    boolean stillDead;
    Location spawn;

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        System.out.println("Resurrection: A player has died!");
        Player p = e.getEntity();
        stillDead = true;
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
                stillDead = false;
                for (PotionEffect effect : p.getActivePotionEffects())
                    p.removePotionEffect(effect.getType());
                p.setGameMode(GameMode.SURVIVAL);
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0);
                }
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + p.getDisplayName() + " has resurrected!");
            }
        }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 1728000);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (stillDead) {
            final Player p = e.getPlayer();
            p.setGameMode(GameMode.SPECTATOR);
            p.sendTitle("You have died!", "You must wait 24 hours before you can respawn.");
            new BukkitRunnable() {
                @Override
                public void run() {
                    spawn = p.getLocation();
//                PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 1728000, 10, false);
                    PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 999999999, 10, false);
                    PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 999999999, 10, false);
//                invisibility.apply(p);
                    blindness.apply(p);
                    slowness.apply(p);
                }
            }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 1);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.SPECTATOR) {
            p.teleport(this.spawn);
        }
    }
}
