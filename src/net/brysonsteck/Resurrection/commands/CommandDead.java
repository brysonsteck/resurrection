package net.brysonsteck.Resurrection.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.brysonsteck.Resurrection.Resurrection;
import net.brysonsteck.Resurrection.player.PlayerData;
import net.brysonsteck.Resurrection.player.TimeCheck;

public class CommandDead implements CommandExecutor {
    boolean DEBUG;

    public CommandDead(String DEBUG) {
        this.DEBUG = Boolean.parseBoolean(DEBUG);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Logger log = JavaPlugin.getProvidingPlugin(Resurrection.class).getLogger();
        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: The `/about` command was ran by " + commandSender.getName());
        }

        PlayerData playerData = new PlayerData();
        playerData.readData();
        String rawData = playerData.getRawData();
        String[] rawPlayers = rawData.split(";");
        int amountDead = 0;
        for (String players : rawPlayers) {
            String[] playerSplit = players.split(",");
            if (playerSplit.length == 3) {
                boolean dead = Boolean.parseBoolean(playerSplit[1]);
                if (dead) {
                    amountDead++;
                }
            }
        }
        String[] responses = new String[amountDead];
        int index = 0;
        for (String players : rawPlayers) {
            String[] playerSplit = players.split(",");
            if (playerSplit.length == 3) {
                String playerName = playerSplit[0];
                boolean dead = Boolean.parseBoolean(playerSplit[1]);
                long timeToResurrection = Long.parseLong(playerSplit[2]);

                if (dead) {
                    TimeCheck timeCheck = new TimeCheck(timeToResurrection - System.currentTimeMillis());
                    if (System.currentTimeMillis() > timeToResurrection) {
                        responses[index] = playerName + " will resurrect when they rejoin.";
                    } else {
                        responses[index] = playerName + " will resurrect in " + timeCheck.formatTime('f');
                    }
                    index++;
                }
                
            }
        }

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (amountDead == 0) {
                p.sendMessage(ChatColor.YELLOW + "There are currently no players awaiting resurrection.");
            } else if (amountDead == 1) {
                p.sendMessage(ChatColor.YELLOW + "There is currently 1 player awaiting resurrection:");
            } else if (amountDead >= 2) {
                p.sendMessage(ChatColor.YELLOW + "There are currently " + amountDead + " players awaiting resurrection:");
            }
            if (amountDead > 0) {
                for (String response : responses) {
                    p.sendMessage(response);
                }
            }
            
        } else {
            if (amountDead == 0) {
                log.info("There are currently no players awaiting resurrection.");
            } else if (amountDead == 1) {
                log.info("There is currently 1 player awaiting resurrection:");
            } else if (amountDead >= 2) {
                log.info("There are currently " + amountDead + " players awaiting resurrection:");
            }
            if (amountDead > 0) {
                for (String response : responses) {
                    log.info(response);
                }
            }
        }

        return true;
    }
    
}
