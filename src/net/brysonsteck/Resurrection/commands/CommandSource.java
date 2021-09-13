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

public class CommandSource implements CommandExecutor {
    boolean DEBUG;

    public CommandSource(String debug) {
        this.DEBUG = Boolean.parseBoolean(debug);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: The `/source` command was ran by " + commandSender.getName());
        }

        if (commandSender instanceof Player) {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is a player");
            }
            commandSender.sendMessage(ChatColor.YELLOW + "Resurrection is FREE AND OPEN SOURCE under the");
            commandSender.sendMessage(ChatColor.YELLOW + "GNU Affero General Public License v3.0 via GitHub.");
            commandSender.sendMessage(ChatColor.YELLOW + "You can view the repository at " + ChatColor.AQUA + "https://github.com/brysonsteck/resurrection");
            commandSender.sendMessage(ChatColor.YELLOW + "and the license at " + ChatColor.AQUA + "https://github.com/brysonsteck/resurrection/blob/master/LICENSE");

            return true;
        } else {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is console");
            }
            System.out.println("[Resurrection] Resurrection is FREE AND OPEN SOURCE under the");
            System.out.println("[Resurrection] GNU Affero General Public License v3.0 via GitHub.");
            System.out.println("[Resurrection] You can view the repository at https://github.com/brysonsteck/resurrection");
            System.out.println("[Resurrection] and the license at https://github.com/brysonsteck/resurrection/blob/master/LICENSE");

            return true;
        }
    }
}
