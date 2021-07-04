package net.brysonsteck.Resurrection.commands;

import net.brysonsteck.Resurrection.startup.CheckForUpdate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAbout implements CommandExecutor {
    String currentVersion;
    boolean outdated;

    public CommandAbout(String currentVersion, boolean outdated) {
        this.currentVersion = currentVersion;
        this.outdated = outdated;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String aboutMessage = ChatColor.GREEN + "" + ChatColor.BOLD + "Resurrection\n\n" + ChatColor.RESET +
                "Resurrection is a Spigot Minecraft plugin that forces players to wait 24 hours before respawning.\n" +
                "The current version of this plugin is " + currentVersion + ".\n\n" +
                "This plugin is licensed under the GNU Affero General Public License v3.0. Read more here: " +
                "For more information on this plugin or to download it for yourself, visit the GitHub repository at https://github.com/brysonsteck/resurrection" +
                "\u00a9 2021 Bryson Steck.";

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Resurrection" + ChatColor.RESET);
            p.sendMessage("");
            p.sendMessage(ChatColor.YELLOW + "Resurrection is a Spigot Minecraft plugin that forces players to wait 24 hours before respawning.");
            p.sendMessage(ChatColor.YELLOW + "This server is running version " + ChatColor.AQUA + currentVersion + ChatColor.YELLOW + " of Resurrection.");
            p.sendMessage("");
            if (outdated) {
                p.sendMessage(ChatColor.RED + "A newer version of this plugin is available. Please notify a server admin to update this plugin for new features and/or stability improvements.");
                p.sendMessage("");
            }
            p.sendMessage(ChatColor.YELLOW + "\u00a9 2021 Bryson Steck");
            p.sendMessage(ChatColor.YELLOW + "This plugin is licensed under the GNU Affero General Public License v3.0. Read more here: ");
            p.sendMessage(ChatColor.YELLOW + "For more information on this plugin or to download it for yourself, visit the GitHub repository at https://github.com/brysonsteck/resurrection");
        } else {
            System.out.println(aboutMessage);
        }
        return true;
    }
}
