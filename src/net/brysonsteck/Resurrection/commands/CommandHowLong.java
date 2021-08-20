package net.brysonsteck.Resurrection.commands;

import net.brysonsteck.Resurrection.player.PlayerData;
import net.brysonsteck.Resurrection.player.TimeCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHowLong implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            boolean self = false;
            boolean valid = false;

            if (strings.length == 0) {
                self = true;
                valid = true;
            } else if (strings.length == 1) {
                valid = true;
            }

            if (valid) {
                Player p;
                if (self) {
                    p = (Player) commandSender;
                } else {
                    p = Bukkit.getPlayer(strings[0]);
                    if (p == null) {
                        commandSender.sendMessage(ChatColor.RED + "ERROR: Player does not exist or is offline!");
                        return false;
                    }
                }
                PlayerData playerData = new PlayerData();
                playerData.readData();
                String rawData = playerData.getRawData();
                String[] rawPlayers = rawData.split(";");
                for (String players : rawPlayers) {
                    if (players.startsWith(p.getDisplayName())) {
                        String[] playerSplit = players.split(",");
                        if (Boolean.parseBoolean(playerSplit[1])) {
                            long currentTime = System.currentTimeMillis();
                            long resurrectionTime = Long.parseLong(playerSplit[2]);

                            TimeCheck timeCheck = new TimeCheck(resurrectionTime - currentTime);
                            if (self) {
                                commandSender.sendMessage("You will respawn in " + timeCheck.formatTime('f'));
                            } else {
                                commandSender.sendMessage(p.getDisplayName() + " will respawn in " + timeCheck.formatTime('f'));
                            }
                            return true;
                        } else {
                            if (self) {
                                commandSender.sendMessage("You aren't dead, dummy.");
                            } else {
                                commandSender.sendMessage("ERROR: " + p.getDisplayName() + " is not dead!");
                            }
                            return false;
                        }
                    }
                }
                commandSender.sendMessage("ERROR: An error has occurred while trying to get time information. This is a bug in the program and not your fault.");
                return false;
            }
        } else {
            boolean valid = false;

            if (strings.length == 0) {
                System.out.println("[Resurrection] ERROR: The /howlong command requires the name of a player when ran through the console.");
                return false;
            } else if (strings.length == 1) {
                valid = true;
            }

            if (valid) {

                Player p = Bukkit.getPlayer(strings[0]);
                if (p == null) {
                    System.out.println(ChatColor.RED + "[Resurrection] ERROR: Player does not exist or is offline!");
                    return false;
                }

                PlayerData playerData = new PlayerData();
                playerData.readData();
                String rawData = playerData.getRawData();
                String[] rawPlayers = rawData.split(";");
                for (String players : rawPlayers) {
                    if (players.startsWith(p.getDisplayName())) {
                        String[] playerSplit = players.split(",");
                        if (Boolean.parseBoolean(playerSplit[1])) {
                            long currentTime = System.currentTimeMillis();
                            long resurrectionTime = Long.parseLong(playerSplit[2]);

                            TimeCheck timeCheck = new TimeCheck(resurrectionTime - currentTime);

                            System.out.println("[Resurrection] " + p.getDisplayName() + " will respawn in " + timeCheck.formatTime('f'));

                            return true;
                        } else {
                            System.out.println("[Resurrection] ERROR: " + p.getDisplayName() + " is not dead!");
                            return false;
                        }
                    }
                }
                System.out.println("[Resurrection] ERROR: An error has occurred while trying to get time information. This is a bug in the program and not your fault.");
                return false;
            }
        }
        return false;
    }
}
