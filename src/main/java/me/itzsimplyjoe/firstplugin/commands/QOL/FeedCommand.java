package me.itzsimplyjoe.firstplugin.commands.QOL;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class FeedCommand implements CommandExecutor {

    static Firstplugin plugin;

    public FeedCommand(Firstplugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            if (args.length == 0){
                Player p = (Player) commandSender;
                if (p.hasPermission("firstplugin.feed")) {
                    if (p.getFoodLevel() < 15) {
                        p.setFoodLevel(20);
                        p.sendMessage("§7You are no longer hungry");
                    } else {
                        p.setFoodLevel(20);
                    }
                }else{
                    p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
                }
            }else{
                    String playerName = args[0];
                    Player target = Bukkit.getServer().getPlayerExact(playerName);
                    Player p = (Player) commandSender;
                    if (p.hasPermission("firstplugin.feed.others")){
                        if (target == null){
                            p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-player-not-found")));
                        }else{
                            target.setFoodLevel(20);
                        }
                    }else{
                        p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
                    }
                }
        }else{
            System.out.println("Only a player can run this command");
        }
        return true;
    }
}
