package net.brysonsteck.Resurrection.player;

import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Hashtable;

public class PlayerListener implements Listener {

    boolean stillDead;
    boolean timerRunning = false;
    World world = Bukkit.getWorlds().get(0);
    Location spawn = world.getSpawnLocation();
    Hashtable<String, Location> playerSpawns = new Hashtable<>();

    public PlayerListener() {
        double newY = 46;
        while(true) {
            Location testLocation = new Location (world, 0, newY, 0);
            Block block = testLocation.getBlock();
            if (block.getType() == Material.AIR) {
                newY++;
                System.out.println("The spawn block at X0 Z0 is Y" + newY);
//                spawn = testLocation;
                break;
            } else {
                newY++;
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerData playerData = new PlayerData();
        playerData.readData();
        String rawData = playerData.getRawData();
        String[] rawPlayers = rawData.split(";");
        int index = 0;
        boolean found = false;
        boolean resumeDeath = false;
        long resurrectTime = 0;
        for (String players : rawPlayers) {
            if (players.startsWith(p.getDisplayName())) {
                found = true;
                String[] playerSplit = players.split(",");
                boolean dead = Boolean.parseBoolean(playerSplit[1]);
                resurrectTime = Long.parseLong(playerSplit[2]);

                if (!dead) {
                    for (PotionEffect effect : p.getActivePotionEffects())
                        p.removePotionEffect(effect.getType());
                    p.setGameMode(GameMode.SURVIVAL);
                } else {
                    resumeDeath = true;
                }

                // save data
                rawPlayers[index] = String.join(",", playerSplit);
                rawData = String.join(";", rawPlayers);
                playerData.saveData(rawData);
                break;
            }
            index++;
        }
        if (!found) {
            playerData.saveData(rawData + ";" + p.getDisplayName() + ",false,0");
        }
        if (resumeDeath && !timerRunning) {
//            spawn = p.getLocation();
            p.sendMessage(ChatColor.RED + "You are still dead. To check how long you have left before you are resurrected, " +
                    "run \"howlong\" in chat.");
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.setGameMode(GameMode.SPECTATOR);
                    PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 999999999, 10, false);
                    PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 999999999, 10, false);
                    blindness.apply(p);
                    slowness.apply(p);
                    // convert to seconds and to ticks
                }
            }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 1);
            resurrectTime = resurrectTime - System.currentTimeMillis();
            // to seconds
            resurrectTime = resurrectTime / 1000;
            // to ticks
            resurrectTime = resurrectTime * 20;

            new BukkitRunnable() {
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
                    if (p.getBedSpawnLocation() != null) {
                        p.teleport(p.getBedSpawnLocation());
                    }
                }
            }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), resurrectTime);
        }

    }

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
//        long timeOfDeath = System.currentTimeMillis();
        long resurrectionTime = System.currentTimeMillis() + 86400000;

        p.sendMessage("You have died!! You will be able to respawn in the next 24 hours.");
        timerRunning = true;

        // save death state
        PlayerData playerData = new PlayerData();
        playerData.readData();
        String rawData = playerData.getRawData();
        String[] rawPlayers = rawData.split(";");
        int index = 0;
        for (String players : rawPlayers) {
            if (players.startsWith(p.getDisplayName())) {
                String[] playerSplit = players.split(",");
                playerSplit[1] = "true";
                playerSplit[2] = String.valueOf(resurrectionTime);

                // save data
                rawPlayers[index] = String.join(",", playerSplit);
                rawData = String.join(";", rawPlayers);
                playerData.saveData(rawData);
                break;
            }
            index++;
        }


        new BukkitRunnable() {
            // save death information to player file
            @Override
            public void run() {
                // save death to false
                String rawData = playerData.getRawData();
                int index = 0;
                for (String players : rawPlayers) {
                    if (players.startsWith(p.getDisplayName())) {
                        String[] playerSplit = players.split(",");
                        playerSplit[1] = "false";
                        playerSplit[2] = "0";

                        rawPlayers[index] = String.join(",", playerSplit);
                        rawData = String.join(";", rawPlayers);
                        playerData.saveData(rawData);
                        break;
                    }
                    index++;
                }
                stillDead = false;
                for (PotionEffect effect : p.getActivePotionEffects())
                    p.removePotionEffect(effect.getType());
                p.setGameMode(GameMode.SURVIVAL);
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0);
                }
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + p.getDisplayName() + " has resurrected!");
                if (p.getBedSpawnLocation() != null) {
                    p.teleport(p.getBedSpawnLocation());
                }
            }
        }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 1728000);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (stillDead) {
            final Player p = e.getPlayer();
            playerSpawns.put(p.getDisplayName(), p.getLocation());
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU HAVE DIED!!");
            p.sendMessage(ChatColor.RED + "You will be able to respawn in the next 24 hours.");
            p.teleport(spawn);
            new BukkitRunnable() {
                @Override
                public void run() {
//                    spawn = p.getLocation();
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
            p.teleport(spawn);
        }
    }
}
