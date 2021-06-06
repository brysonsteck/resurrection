package net.brysonsteck.Resurrection.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
                    p.sendMessage("That player does not exist! Failed to resurrect.");
                    return false;
                }
                if (resurrectPlayer.getGameMode() == GameMode.ADVENTURE) {
                    for (PotionEffect effect : resurrectPlayer.getActivePotionEffects())
                        resurrectPlayer.removePotionEffect(effect.getType());
                    resurrectPlayer.setGameMode(GameMode.SURVIVAL);
                    Bukkit.broadcastMessage(strings[0] + " has been resurrected manually by an admin!");
                    return true;
                } else {
                    p.sendMessage(strings[0] + " is not dead! Failed to resurrect.");
                    return false;
                }
            } else {
                System.out.println("Too few arguments!");
                System.out.println("Usage: /resurrect PLAYER");
                return false;
            }
        } else {
            if (valid) {
                Player resurrectPlayer = Bukkit.getPlayer(strings[0]);
                if (resurrectPlayer == null) {
                    System.out.println("That player does not exist! Failed to resurrect.");
                    return false;
                }
                if (resurrectPlayer.getGameMode() == GameMode.ADVENTURE) {
                    for (PotionEffect effect : resurrectPlayer.getActivePotionEffects())
                        resurrectPlayer.removePotionEffect(effect.getType());
                    resurrectPlayer.setGameMode(GameMode.SURVIVAL);
                    Bukkit.broadcastMessage(strings[0] + " has been resurrected manually by an admin!");
                    return true;
                } else {
                    System.out.println(strings[0] + " is not dead! Failed to resurrect.");
                    return false;
                }
            } else {
                System.out.println("Too few arguments!");
                System.out.println("Usage: /resurrect PLAYER");
                return false;
            }
        }
    }
}
