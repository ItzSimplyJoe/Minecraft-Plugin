package me.itzsimplyjoe.firstplugin.Listeners;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.util.List;

public class OnJoin implements Listener {
    private final Firstplugin plugin;

    public OnJoin(Firstplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        for (int i = 0; i < plugin.vanishedPlayers.size(); i++){
            p.hidePlayer(plugin, plugin.vanishedPlayers.get(i));
        }
        Player player = event.getPlayer();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("scoreboard-name"));
        Objective objective = scoreboard.registerNewObjective("Test", "dummy", title );
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> scoreboardText = plugin.getConfig().getStringList("scoreboard-text");
        for (int i = 0; i <= scoreboardText.size() - 1; i++) {
            String text = scoreboardText.get(i)
                    .replace("{player}", p.getDisplayName())
                    .replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()));
            Score score = objective.getScore(ChatColor.translateAlternateColorCodes('&', text));
            score.setScore(scoreboardText.size() - i);

            p.setScoreboard(scoreboard);
        }

        if (p.hasPlayedBefore()){
            String join = (ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("join-broadcast")));
            join = join.replace("{player}", p.getDisplayName());
            event.setJoinMessage(join);
            if (plugin.getConfig().getBoolean("motd")){
                for (int i = 0; i < plugin.getConfig().getList("motd-message").size(); i++){
                    String message = (ChatColor.translateAlternateColorCodes('&' ,plugin.getConfig().getList("motd-message").get(i).toString()));
                    message = message.replace("{player}", p.getDisplayName());
                    p.sendMessage(message);
                }
            }
        }else{
            Location location = plugin.getConfig().getLocation("spawn");

            if (location != null){
                p.teleport(location);
            }
            String firstjoin = (ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("first-join-broadcast")));
            firstjoin = firstjoin.replace("{player}", p.getDisplayName());
            event.setJoinMessage(firstjoin);
            String privatejoinmessage = (ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("first-join-message")));
            privatejoinmessage = privatejoinmessage.replace("{player}", p.getDisplayName());
            p.sendMessage(privatejoinmessage);

        }


    }
}
