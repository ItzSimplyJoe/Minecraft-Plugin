package me.itzsimplyjoe.firstplugin.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()){
            event.setJoinMessage("§7[§a+§7] §f" + player.getDisplayName());
            player.sendMessage("§7Hello §b" + player.getDisplayName() + "§7Welcome back to our server!");
        }else{
            event.setJoinMessage("§b§l" + player.getDisplayName() + "§fhas joined for the first time!");
            player.sendMessage("§7Hello §b" + player.getDisplayName() + "§7Welcome to our server!");

        }


    }
}
