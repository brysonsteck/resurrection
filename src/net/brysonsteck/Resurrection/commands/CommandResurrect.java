package net.brysonsteck.Resurrection.commands;

import net.brysonsteck.Resurrection.Resurrection;
import net.brysonsteck.Resurrection.player.PlayerData;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class CommandResurrect implements CommandExecutor {
    boolean DEBUG;

    public CommandResurrect(String debug) {
        this.DEBUG = Boolean.parseBoolean(debug);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Logger log = JavaPlugin.getProvidingPlugin(Resurrection.class).getLogger();
        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: The `resurrect` command was ran by " + commandSender.getName());
        }

        boolean valid = (strings.length == 1);

        
        if (commandSender instanceof Player) {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is a player.");
            }

            Player p = (Player) commandSender;
            if (valid) {
                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Valid; an argument was specified. Assuming it as name of player to resurrect");
                }

                Player resurrectPlayer = Bukkit.getPlayer(strings[0]);
                if (resurrectPlayer == null) {
                    p.sendMessage(ChatColor.RED + "ERROR: That player is not online/doesn't exist! Failed to resurrect.");
                    return false;
                }
                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Specified player \"" + resurrectPlayer.getDisplayName() + "\" exists");
                }

                if (resurrectPlayer.getGameMode() == GameMode.SPECTATOR) {
                    if (DEBUG) {
                        Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Specified player in spectator mode, assuming dead");
                    }
                    for (PotionEffect effect : resurrectPlayer.getActivePotionEffects())
                        resurrectPlayer.removePotionEffect(effect.getType());
                    resurrectPlayer.setGameMode(GameMode.SURVIVAL);
                    for(Player player : Bukkit.getOnlinePlayers()){
                        try {
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, 0);
                        } catch (NoSuchFieldError e) {
                            log.warning("NoSuchFieldError encountered, playing Wither noise instead.");
                            player.playSound(player.getLocation(), Sound.WITHER_DEATH, 1, 0);
                        }
                    }
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + strings[0] + " has been resurrected manually by an admin!");
                    removeDeath(resurrectPlayer);
                    if (p.getBedSpawnLocation() != null) {
                        if (DEBUG) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: A bed for the specified player was found. Teleporting");
                        }

                        p.teleport(p.getBedSpawnLocation());
                    }
                    return true;
                } else {
                    p.sendMessage(ChatColor.RED + "ERROR: " + strings[0] + " is not dead! Failed to resurrect.");
                    return false;
                }
            } else {
                p.sendMessage(ChatColor.RED + "ERROR: Too few arguments!");
                return false;
            }
        } else {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is console.");
            }

            if (valid) {
                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Valid; an argument was specified. Assuming it as name of player to resurrect");
                }

                Player resurrectPlayer = Bukkit.getPlayer(strings[0]);
                if (resurrectPlayer == null) {
                    log.warning("ERROR: That player is not online/doesn't exist! Failed to resurrect.");
                    return false;
                }
                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Specified player \"" + resurrectPlayer.getDisplayName() + "\" exists");
                }

                if (resurrectPlayer.getGameMode() == GameMode.SPECTATOR) {
                    if (DEBUG) {
                        Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Specified player in spectator mode, assuming dead");
                    }
                    for (PotionEffect effect : resurrectPlayer.getActivePotionEffects())
                        resurrectPlayer.removePotionEffect(effect.getType());
                    resurrectPlayer.setGameMode(GameMode.SURVIVAL);
                    for(Player player : Bukkit.getOnlinePlayers()){
                        try {
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, 0);
                        } catch (NoSuchFieldError e) {
                            log.warning("NoSuchFieldError encountered, playing Wither noise instead.");
                            player.playSound(player.getLocation(), Sound.WITHER_DEATH, 1, 0);
                        }
                    }
                    Bukkit.broadcastMessage(strings[0] + " has been resurrected manually by an admin!");
                    removeDeath(resurrectPlayer);
                    if (resurrectPlayer.getBedSpawnLocation() != null) {
                        if (DEBUG) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: A bed for the specified player was found. Teleporting");
                        }

                        resurrectPlayer.teleport(resurrectPlayer.getBedSpawnLocation());
                    }
                    return true;
                } else {
                    log.warning("ERROR: " + strings[0] + " is not dead! Failed to resurrect.");
                    return false;
                }
            } else {
                log.warning("ERROR: Too few arguments!");
                return false;
            }
        }
    }

    public void removeDeath(Player p) {
        PlayerData playerData = new PlayerData();
        playerData.readData();
        String rawData = playerData.getRawData();
        String[] rawPlayers = rawData.split(";");
        int index = 0;
        for (String players : rawPlayers) {
            if (players.startsWith(p.getDisplayName())) {
                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Removing the death from the resurrected player");
                }
                String[] playerSplit = players.split(",");
                playerSplit[1] = "false";
                playerSplit[2] = "0";

                // save data
                rawPlayers[index] = String.join(",", playerSplit);
                rawData = String.join(";", rawPlayers);
                playerData.saveData(rawData);
                break;
            }
            index++;
        }
    }
}
