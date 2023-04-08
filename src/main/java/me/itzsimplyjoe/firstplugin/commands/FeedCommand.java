package me.itzsimplyjoe.firstplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {
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
                    p.sendMessage("§cYou don't have permission to run this command");
                }
            }else{
                    String playerName = args[0];
                    Player target = Bukkit.getServer().getPlayerExact(playerName);
                    Player p = (Player) commandSender;
                    if (p.hasPermission("firstplugin.feed.others")){
                        if (target == null){
                            p.sendMessage("This player isn't online");
                        }else{
                            target.setFoodLevel(20);
                        }
                    }else{
                        p.sendMessage("§cYou don't have permission to run this command");
                    }
                }
        }else{
            System.out.println("Only a player can run this command");
        }
        return true;
    }
}
