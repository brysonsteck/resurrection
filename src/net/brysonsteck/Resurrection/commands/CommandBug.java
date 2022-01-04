package net.brysonsteck.Resurrection.commands;

import net.brysonsteck.Resurrection.Resurrection;

import java.util.logging.Logger;

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
        Logger log = JavaPlugin.getProvidingPlugin(Resurrection.class).getLogger();
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

            log.info("Did you find a bug? Well that sucks for you.");
            new BukkitRunnable() {
                @Override
                public void run() {
                    log.info("");
                    log.info("Okay, fine. Maybe I'll tell you how to fix the problem. Hehe.");
                    log.info("You can either create an issue on GitHub here: https://github.com/brysonsteck/resurrection/issues OR you can fill out this Google Form if you don't know how to use GitHub. Please prepare to explain how the bug occurred regardless of how you report the bug to me: https://forms.gle/3gLmhMXowNyqKUGdA ");
                }
            }.runTaskLater(JavaPlugin.getProvidingPlugin(Resurrection.class), 60);
            return true;
        }
    }
}
