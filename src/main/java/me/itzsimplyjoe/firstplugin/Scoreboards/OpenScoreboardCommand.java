package me.itzsimplyjoe.firstplugin.Scoreboards;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;
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
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = manager.getNewScoreboard();
                String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("scoreboard-name"));
                Objective objective = scoreboard.registerNewObjective("Test", "dummy", title);
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
            }
        } else {
            System.out.println("You must be a player to use this command!");
        }
        return true;
    }

}

