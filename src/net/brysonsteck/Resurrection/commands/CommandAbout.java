package net.brysonsteck.Resurrection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAbout implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String aboutMessage = "This is the about message for Resurrection.";
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.sendMessage(aboutMessage);
        } else {
            System.out.println(aboutMessage);
        }
        return true;
    }
}
