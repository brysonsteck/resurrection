package net.brysonsteck.Resurrection.commands;

import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandBug implements CommandExecutor {
    boolean DEBUG;

    public CommandBug(String debug) {
        this.DEBUG = Boolean.parseBoolean(debug);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: The `/bug` command was ran by " + commandSender.getName());
        }

        if (commandSender instanceof Player) {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is a player.");
            }

            commandSender.sendMessage(ChatColor.YELLOW + "Did you find a bug? Well that sucks for you.");
            new BukkitRunnable() {
                @Override
                public void run() {
                    commandSender.sendMessage("");
                    commandSender.sendMessage(ChatColor.YELLOW + "Okay, fine. Maybe I'll tell you how to fix the problem. Hehe.");
                    commandSender.sendMessage(ChatColor.YELLOW + "You can either create an issue on GitHub here: " + ChatColor.AQUA + "https://github.com/brysonsteck/resurrection/issues");
                    commandSender.sendMessage(ChatColor.YELLOW + "OR you can fill out this Google Form if you don't know how to use GitHub: " + ChatColor.AQUA + "https://forms.gle/3gLmhMXowNyqKUGdA");
                    commandSender.sendMessage(ChatColor.YELLOW + "Please prepare to explain how the bug occurred regardless of how you report the bug to me.");
                }
            }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 60);
            return true;
        } else {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is console");
            }

            System.out.println("[Resurrection] Did you find a bug? Well that sucks for you.");
            new BukkitRunnable() {
                @Override
                public void run() {
                    System.out.println("[Resurrection] ");
                    System.out.println("[Resurrection] Okay, fine. Maybe I'll tell you how to fix the problem. Hehe.");
                    System.out.println("[Resurrection] You can either create an issue on GitHub here: https://github.com/brysonsteck/resurrection/issues");
                    System.out.println("[Resurrection] OR you can fill out this Google Form if you don't know how to use GitHub: https://forms.gle/3gLmhMXowNyqKUGdA");
                    System.out.println("[Resurrection] Please prepare to explain how the bug occurred regardless of how you report the bug to me.");
                }
            }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 60);
            return true;
        }
    }
}
