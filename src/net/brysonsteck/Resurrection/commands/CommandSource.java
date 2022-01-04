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


public class CommandSource implements CommandExecutor {
    boolean DEBUG;

    public CommandSource(String debug) {
        this.DEBUG = Boolean.parseBoolean(debug);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Logger log = JavaPlugin.getProvidingPlugin(Resurrection.class).getLogger();
        if (DEBUG) {
            Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: The `/source` command was ran by " + commandSender.getName());
        }

        if (commandSender instanceof Player) {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is a player");
            }
            commandSender.sendMessage(ChatColor.YELLOW + "Resurrection is FREE AND OPEN SOURCE under the GNU Affero General Public License v3.0 via GitHub. You can view the repository at " + ChatColor.AQUA + "https://github.com/brysonsteck/resurrection" + ChatColor.YELLOW + " and the license at " + ChatColor.AQUA + "https://github.com/brysonsteck/resurrection/blob/master/LICENSE");

            return true;
        } else {
            if (DEBUG) {
                Bukkit.broadcastMessage(ChatColor.YELLOW  +""+ ChatColor.BOLD + "[Res. DEBUG]: CommandSender is console");
            }
            log.info("Resurrection is FREE AND OPEN SOURCE under the GNU Affero General Public License v3.0 via GitHub. You can view the repository at https://github.com/brysonsteck/resurrection and the license at https://github.com/brysonsteck/resurrection/blob/master/LICENSE");
            return true;
        }
    }
}
