package me.itzsimplyjoe.firstplugin.Listeners;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.MuteUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteListener implements Listener {
    private Firstplugin plugin;
    public MuteListener(Firstplugin plugin) {

        this.plugin = plugin;
    }

    private MuteUtils config;

    public MuteListener(MuteUtils config) {
        this.config = config;
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (config.isMuted(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-is-muted")));
        }
    }

}
