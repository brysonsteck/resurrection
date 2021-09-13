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
    boolean DEBUG;

    public CommandHowLong(String debug) {
        this.DEBUG = Boolean.parseBoolean(debug);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: The `/howlong` command was ran by " + commandSender.getName());
        }

        if (commandSender instanceof Player) {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is a player.");
            }

            boolean self = false;
            boolean valid = false;

            if (strings.length == 0) {
                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Valid; no arguments given. Assuming sender wants to check own time");
                }

                self = true;
                valid = true;
            } else if (strings.length == 1) {
                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Valid; one argument given. Assuming sender wants to check another player's time");
                }

                valid = true;
            }

            if (valid) {
                Player p;
                if (self) {
                    p = (Player) commandSender;
                } else {
                    p = Bukkit.getPlayer(strings[0]);
                    if (p == null) {
                        commandSender.sendMessage(ChatColor.RED + "ERROR: That player is not online/doesn't exist!");
                        return false;
                    }

                    if (DEBUG) {
                        Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Specified player " + p.getDisplayName() + " exists.");
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
                            if (DEBUG) {
                                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Player is dead according to file. Calculating time until resurrection");
                            }

                            long currentTime = System.currentTimeMillis();
                            long resurrectionTime = Long.parseLong(playerSplit[2]);

                            TimeCheck timeCheck = new TimeCheck(resurrectionTime - currentTime);
                            if (self) {
                                commandSender.sendMessage(ChatColor.YELLOW + "You will respawn in " + timeCheck.formatTime('f'));
                            } else {
                                commandSender.sendMessage(ChatColor.YELLOW + p.getDisplayName() + " will respawn in " + timeCheck.formatTime('f'));
                            }
                            return true;
                        } else {
                            if (self) {
                                commandSender.sendMessage(ChatColor.RED + "ERROR: You aren't dead, dummy.");
                            } else {
                                commandSender.sendMessage(ChatColor.RED + "ERROR: " + p.getDisplayName() + " is not dead!");
                            }
                            return false;
                        }
                    }
                }
                commandSender.sendMessage(ChatColor.RED + "ERROR: An error has occurred while trying to get time information. This is a bug in the program and not your fault.");
                return false;
            }
        } else {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is console.");
            }

            boolean valid = false;

            if (strings.length == 0) {
                System.out.println("[Resurrection] ERROR: The /howlong command requires the name of a player when ran through the console.");
                return false;
            } else if (strings.length == 1) {
                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Valid; console specified player to check.");
                }

                valid = true;
            }

            if (valid) {

                Player p = Bukkit.getPlayer(strings[0]);
                if (p == null) {
                    System.out.println("[Resurrection] ERROR: That player is not online/doesn't exist!");
                    return false;
                }

                if (DEBUG) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Specified player " + p.getDisplayName() + " exists.");
                }

                PlayerData playerData = new PlayerData();
                playerData.readData();
                String rawData = playerData.getRawData();
                String[] rawPlayers = rawData.split(";");
                for (String players : rawPlayers) {
                    if (players.startsWith(p.getDisplayName())) {
                        String[] playerSplit = players.split(",");
                        if (Boolean.parseBoolean(playerSplit[1])) {
                            if (DEBUG) {
                                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: Player is dead according to file. Calculating time until resurrection");
                            }
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
