package me.itzsimplyjoe.firstplugin.Listeners;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeave implements Listener {
    private final Firstplugin plugin;

    public OnLeave(Firstplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String join = (ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("leave-broadcast")));
        join = join.replace("{player}", player.getDisplayName());
        event.setQuitMessage(join);
    }
}
