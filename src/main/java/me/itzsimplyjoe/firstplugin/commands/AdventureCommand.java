package me.itzsimplyjoe.firstplugin.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdventureCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (p.hasPermission("firstplugin.adventure")) {
                if (p.getGameMode() == GameMode.ADVENTURE) {
                    p.sendMessage("§cYou are already in adventure");
                } else {
                    p.setGameMode(GameMode.ADVENTURE);
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
