package me.itzsimplyjoe.firstplugin.commands.Gamemodes;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdventureCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            if (args.length == 0){
                Player p = (Player) commandSender;
                if (p.hasPermission("firstplugin.adventure")) {
                    if (p.getGameMode() == GameMode.ADVENTURE) {
                        p.sendMessage("§cYou are already in adventure");
                    } else {
                        p.setGameMode(GameMode.ADVENTURE);
                    }
                }else{
                    p.sendMessage("§cYou don't have permission to run this command");
                }
            }else{
                String playerName = args[0];
                Player target = Bukkit.getServer().getPlayerExact(playerName);
                Player p = (Player) commandSender;
                if (p.hasPermission("firstplugin.adventure.others")){
                    if (target == null){
                        p.sendMessage("This player isn't online");
                    }else{
                        if (target.getGameMode() == GameMode.ADVENTURE) {
                            p.sendMessage("§cThis player is already in adventure");
                        } else {
                            target.setGameMode(GameMode.ADVENTURE);
                        }
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
