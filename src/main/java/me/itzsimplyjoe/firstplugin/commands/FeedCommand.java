package me.itzsimplyjoe.firstplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (p.hasPermission("firstplugin.feed")) {
                if (p.getFoodLevel() < 15) {
                    p.setFoodLevel(20);
                    p.sendMessage("§7You are no longer hungry");
                } else {
                    p.setFoodLevel(20);
                }
            }else{
                p.sendMessage("§You don't have permission to run this command");
            }
        }else{
            System.out.println("Only a player can run this command");
        }
        return true;
    }
}
