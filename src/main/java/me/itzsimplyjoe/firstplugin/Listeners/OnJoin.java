package me.itzsimplyjoe.firstplugin.Listeners;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    private final Firstplugin plugin;

    public OnJoin(Firstplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (p.hasPlayedBefore()){
            event.setJoinMessage("§7[§a+§7] §f" + p.getDisplayName());
            p.sendMessage("§7Hello §b" + p.getDisplayName() + "§7Welcome back to our server!");
        }else{
            Location location = plugin.getConfig().getLocation("spawn");

            if (location != null){
                p.teleport(location);
            }
            event.setJoinMessage("§b§l" + p.getDisplayName() + "§fhas joined for the first time!");
            p.sendMessage("§7Hello §b" + p.getDisplayName() + "§7Welcome to our server!");

        }


    }
}
