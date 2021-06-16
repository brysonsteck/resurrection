package net.brysonsteck.Resurrection.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class ResurrectPlayer {

    public ResurrectPlayer(Player p) {
        Bukkit.broadcastMessage(p.getDisplayName() + " has resurrected!");
    }
}
