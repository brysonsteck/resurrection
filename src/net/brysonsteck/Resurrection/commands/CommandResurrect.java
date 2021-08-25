package net.brysonsteck.Resurrection.commands;

import net.brysonsteck.Resurrection.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class CommandResurrect implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        boolean valid = (strings.length == 1);
        
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (valid) {
                Player resurrectPlayer = Bukkit.getPlayer(strings[0]);
                if (resurrectPlayer == null) {
                    p.sendMessage(ChatColor.RED + "ERROR: That player is not online/doesn't exist! Failed to resurrect.");
                    return false;
                }
                if (resurrectPlayer.getGameMode() == GameMode.SPECTATOR) {
                    for (PotionEffect effect : resurrectPlayer.getActivePotionEffects())
                        resurrectPlayer.removePotionEffect(effect.getType());
                    resurrectPlayer.setGameMode(GameMode.SURVIVAL);
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0);
                    }
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + strings[0] + " has been resurrected manually by an admin!");
                    removeDeath(resurrectPlayer);
                    if (p.getBedSpawnLocation() != null) {
                        p.teleport(p.getBedSpawnLocation());
                    }
                    return true;
                } else {
                    p.sendMessage(ChatColor.RED + "ERROR: " + strings[0] + " is not dead! Failed to resurrect.");
                    return false;
                }
            } else {
                System.out.println(ChatColor.RED + "ERROR: Too few arguments!");
                return false;
            }
        } else {
            if (valid) {
                Player resurrectPlayer = Bukkit.getPlayer(strings[0]);
                if (resurrectPlayer == null) {
                    System.out.println("[Resurrection] ERROR: That player is not online/doesn't exist! Failed to resurrect.");
                    return false;
                }
                if (resurrectPlayer.getGameMode() == GameMode.SPECTATOR) {
                    for (PotionEffect effect : resurrectPlayer.getActivePotionEffects())
                        resurrectPlayer.removePotionEffect(effect.getType());
                    resurrectPlayer.setGameMode(GameMode.SURVIVAL);
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0);
                    }
                    Bukkit.broadcastMessage(strings[0] + " has been resurrected manually by an admin!");
                    removeDeath(resurrectPlayer);
                    if (resurrectPlayer.getBedSpawnLocation() != null) {
                        resurrectPlayer.teleport(resurrectPlayer.getBedSpawnLocation());
                    }
                    return true;
                } else {
                    System.out.println("[Resurrection] ERROR: " + strings[0] + " is not dead! Failed to resurrect.");
                    return false;
                }
            } else {
                System.out.println("[Resurrection] ERROR: Too few arguments!");
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
