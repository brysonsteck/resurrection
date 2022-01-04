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

public class CommandAbout implements CommandExecutor {
    boolean DEBUG;
    String currentVersion;
    boolean outdated;

    public CommandAbout(String debug, String currentVersion, boolean outdated) {
        this.DEBUG = Boolean.parseBoolean(debug);
        this.currentVersion = currentVersion;
        this.outdated = outdated;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Logger log = JavaPlugin.getProvidingPlugin(Resurrection.class).getLogger();
        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: The `/about` command was ran by " + commandSender.getName());
        }

        if (commandSender instanceof Player) {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is a player.");
            }

            String outdatedText = "";
            Player p = (Player) commandSender;
            p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "--- Resurrection ---" + ChatColor.RESET);
            p.sendMessage(ChatColor.YELLOW + "Resurrection is a Spigot Minecraft plugin that forces players to wait a certain amount of time before respawning.");
            if (outdated) {
                outdatedText = " HOWEVER, A newer version of this plugin is available. Please notify a server admin to update this plugin for new features and/or stability improvements.";
            }
            p.sendMessage(ChatColor.YELLOW + "This server is running version " + ChatColor.AQUA + currentVersion + ChatColor.YELLOW + " of Resurrection." + ChatColor.RED + outdatedText);
            p.sendMessage("---");
            p.sendMessage(ChatColor.YELLOW + "This plugin is licensed under the GNU Affero General Public License v3.0. For more info, run " + ChatColor.AQUA + "/source");
            p.sendMessage(ChatColor.YELLOW + "For more info on this plugin or to download it, visit the GitHub repository at " + ChatColor.AQUA + "https://github.com/brysonsteck/resurrection");
            p.sendMessage(ChatColor.YELLOW + "\u00a9 2021 Bryson Steck");
        } else {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is console.");
            }
            String outdatedText = "";
            log.info("--- Resurrection ---");
            log.info("");
            log.info("Resurrection is a Spigot Minecraft plugin that forces players to wait a certain amount of time before respawning.");
            if (outdated) {
                outdatedText = " HOWEVER, a newer version of Resurrection is available. Please check the updater on startup for more information.";
            }
            log.info("This server is running version " + currentVersion + " of Resurrection." + outdatedText);
            log.info("");
            log.info("This plugin is licensed under the GNU Affero General Public License v3.0. For more info, run \"/source\". Since you're the admin, you probably know where to download it lmao. Here's the link anyway: https://github.com/brysonsteck/resurrection");
            log.info("Copyright 2021 Bryson Steck");
        }
        return true;
    }
}
