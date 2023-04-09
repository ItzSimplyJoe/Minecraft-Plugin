package me.itzsimplyjoe.firstplugin.Scoreboards;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Objects;

public class OpenScoreboardCommand implements CommandExecutor {
    static Firstplugin plugin;

    public OpenScoreboardCommand(Firstplugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (!commandSender.hasPermission("firstplugin.scoreboard.open")) {
                p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
                return true;
            }else {
                Player player = (Player) commandSender;
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = manager.getNewScoreboard();

                Objective objective = scoreboard.registerNewObjective("Test", "dummy", "Test");
                objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);
                for (int i = 0; i < plugin.getConfig().getList("scoreboard-text").size(); i++) {
                    Score score = objective.getScore(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("scoreboard-text").get(i).toString()));
                    score.setScore(i);
                }
                player.setScoreboard(scoreboard);
            }
        } else {
            System.out.println("You must be a player to use this command!");
        }
        return true;
    }
}

