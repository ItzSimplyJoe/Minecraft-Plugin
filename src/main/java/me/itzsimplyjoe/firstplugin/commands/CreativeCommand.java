package me.itzsimplyjoe.firstplugin.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreativeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (p.hasPermission("firstplugin.creative")) {
                if (p.getGameMode() == GameMode.CREATIVE) {
                    p.sendMessage("§cYou are already in creative");
                } else {
                    p.setGameMode(GameMode.CREATIVE);
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
