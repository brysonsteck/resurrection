package net.brysonsteck.Resurrection.player;

import net.brysonsteck.Resurrection.startup.ParseSettings;
import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.*;
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
    ParseSettings parseSettings;

    public PlayerListener(ParseSettings parseSettings) {
        this.parseSettings = parseSettings;
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
        long timeToResurrection = 0;
        for (String players : rawPlayers) {
            if (players.startsWith(p.getDisplayName())) {
                found = true;
                String[] playerSplit = players.split(",");
                boolean dead = Boolean.parseBoolean(playerSplit[1]);
                timeToResurrection = Long.parseLong(playerSplit[2]);

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
            p.sendMessage(ChatColor.RED + "You are still dead. To check how long you have left before you are resurrected, ");
            p.sendMessage(ChatColor.RED + "run the \"/howlong\" command in chat.");
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.setGameMode(GameMode.SPECTATOR);
                    PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 999999999, 10, false);
                    PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 999999999, 10, false);
                    blindness.apply(p);
                    slowness.apply(p);
                    p.teleport(spawn);
                }
            }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 1);
            timeToResurrection = timeToResurrection - System.currentTimeMillis();
            // to seconds
            timeToResurrection = timeToResurrection / 1000;
            // to ticks
            timeToResurrection = timeToResurrection * 20;

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
            }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), timeToResurrection);
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        System.out.println("[Resurrection] A player has died!");
        Player p = e.getEntity();
        stillDead = true;
        TimeCheck timeCheck = new TimeCheck(Long.parseLong(parseSettings.getSetting("resurrection_time")));

        long resurrectionTime = System.currentTimeMillis() + Long.parseLong(parseSettings.getSetting("resurrection_time"));

        e.setDeathMessage(e.getDeathMessage() + " and will respawn in the next " + timeCheck.formatTime('f'));
//        p.sendMessage("You have died!! You will be able to respawn in the next " + timeCheck.formatTime('h'));
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

        long timeToResurrection = Long.parseLong(parseSettings.getSetting("resurrection_time"));
        // to seconds
        timeToResurrection = timeToResurrection / 1000;
        // to ticks
        timeToResurrection = timeToResurrection * 20;


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
        }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), timeToResurrection);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (stillDead) {
            final Player p = e.getPlayer();
            TimeCheck timeCheck = new TimeCheck(Long.parseLong(parseSettings.getSetting("resurrection_time")));
            playerSpawns.put(p.getDisplayName(), p.getLocation());
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU HAVE DIED!!");
            p.sendMessage(ChatColor.RED + "You will be able to respawn in the next " + timeCheck.formatTime('f'));
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
