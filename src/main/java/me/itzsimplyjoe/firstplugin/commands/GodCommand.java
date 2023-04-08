package me.itzsimplyjoe.firstplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            if (args.length == 0) {
                Player p = (Player) commandSender;
                if (p.hasPermission("firstplugin.god")) {
                    if (p.isInvulnerable()) {
                        p.setInvulnerable(false);
                        p.sendMessage("§cGod Mode has been disabled!");
                    } else {
                        p.setInvulnerable(true);
                        p.sendMessage("§aGod Mode has been enabled");
                    }
                } else {
                    p.sendMessage("§cYou don't have permission to run this command");
                }
            }else{
                String playerName = args[0];
                Player target = Bukkit.getServer().getPlayerExact(playerName);
                Player p = (Player) commandSender;
                if (p.hasPermission("firstplugin.god.others")) {
                    if (target == null) {
                        p.sendMessage("This player isn't online");
                    } else {
                        if (target.isInvulnerable()) {
                            target.setInvulnerable(false);
                            target.sendMessage("§cGod Mode has been disabled!");
                            p.sendMessage("§7God Mode has been disabled for §c" + target);

                        } else {
                            target.setInvulnerable(true);
                            target.sendMessage("§aGod Mode has been enabled");
                            p.sendMessage("§7God Mode has been enabled for §a" + target);
                        }
                    }
                }else {
                    p.sendMessage("§cYou don't have permission to run this command");
                }
            }
                } else {
                System.out.println("Only a player can run this command");
            }

        return true;
    }
}