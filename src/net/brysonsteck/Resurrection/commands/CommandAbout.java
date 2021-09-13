package net.brysonsteck.Resurrection.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAbout implements CommandExecutor {
    boolean debug;
    String currentVersion;
    boolean outdated;

    public CommandAbout(String debug, String currentVersion, boolean outdated) {
        this.debug = Boolean.parseBoolean(debug);
        this.currentVersion = currentVersion;
        this.outdated = outdated;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (debug) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: The `/about` command was ran by " + commandSender.getName());
        }

        if (commandSender instanceof Player) {
            if (debug) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is a player.");
            }
            Player p = (Player) commandSender;
            p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "--- Resurrection ---" + ChatColor.RESET);
            p.sendMessage(ChatColor.YELLOW + "Resurrection is a Spigot Minecraft plugin that forces players to wait a certain amount of time before respawning.");
            p.sendMessage(ChatColor.YELLOW + "This server is running version " + ChatColor.AQUA + currentVersion + ChatColor.YELLOW + " of Resurrection.");
            if (outdated) {
                p.sendMessage(ChatColor.RED + "HOWEVER, A newer version of this plugin is available. Please notify a server admin to update this plugin for new features and/or stability improvements.");
            }
            p.sendMessage("---");
            p.sendMessage(ChatColor.YELLOW + "This plugin is licensed under the GNU Affero General Public License v3.0. For more info, run " + ChatColor.AQUA + "/source");
            p.sendMessage(ChatColor.YELLOW + "For more info on this plugin or to download it, visit the GitHub repository at " + ChatColor.AQUA + "https://github.com/brysonsteck/resurrection");
            p.sendMessage(ChatColor.YELLOW + "\u00a9 2021 Bryson Steck");
        } else {
            if (debug) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is console.");
            }
            System.out.println("[Resurrection] --- Resurrection ---");
            System.out.println("[Resurrection]");
            System.out.println("[Resurrection] Resurrection is a Spigot Minecraft plugin that forces players to wait a certain amount of time before respawning.");
            System.out.println("[Resurrection] This server is running version " + currentVersion + " of Resurrection.");
            if (outdated) {
                System.out.println("[Resurrection] HOWEVER, a newer version of Resurrection is available. Please check the updater on startup for more information.");
            }
            System.out.println("[Resurrection]");
            System.out.println("[Resurrection] This plugin is licensed under the GNU Affero General Public License v3.0. For more info, run /source");
            System.out.println("[Resurrection] Since you're the admin, you probably know where to download it lmao. Here's the link anyway: https://github.com/brysonsteck/resurrection");
            System.out.println("[Resurrection] Copyright 2021 Bryson Steck");

        }
        return true;
    }
}
