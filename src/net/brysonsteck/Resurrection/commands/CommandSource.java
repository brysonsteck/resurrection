package net.brysonsteck.Resurrection.commands;

import net.brysonsteck.Resurrection.Resurrection;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandSource implements CommandExecutor {
    boolean debug;

    public CommandSource(String debug) {
        this.debug = Boolean.parseBoolean(debug);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            commandSender.sendMessage(ChatColor.YELLOW + "Resurrection is FREE AND OPEN SOURCE under the");
            commandSender.sendMessage(ChatColor.YELLOW + "GNU Affero General Public License v3.0 via GitHub.");
            commandSender.sendMessage(ChatColor.YELLOW + "You can view the repository at " + ChatColor.AQUA + "https://github.com/brysonsteck/resurrection");
            commandSender.sendMessage(ChatColor.YELLOW + "and the license at " + ChatColor.AQUA + "https://github.com/brysonsteck/resurrection/blob/master/LICENSE");

            return true;
        } else {
            System.out.println("[Resurrection] Resurrection is FREE AND OPEN SOURCE under the");
            System.out.println("[Resurrection] GNU Affero General Public License v3.0 via GitHub.");
            System.out.println("[Resurrection] You can view the repository at https://github.com/brysonsteck/resurrection");
            System.out.println("[Resurrection] and the license at https://github.com/brysonsteck/resurrection/blob/master/LICENSE");

            return true;
        }
    }
}
