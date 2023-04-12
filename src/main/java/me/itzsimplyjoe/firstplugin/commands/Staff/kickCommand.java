package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class kickCommand implements CommandExecutor {
    private final Firstplugin plugin;
    public kickCommand(Firstplugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender.hasPermission("firstplugin.kick")){
            if (args.length == 0){
                commandSender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-kick").replace("{command}", "/kick <player> <reason>")));
            }else if(args.length == 1){
                commandSender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-reason-for-kick").replace("{command}", "/kick <player> <reason>")));
            }else if(args.length >= 2){
                Player target = commandSender.getServer().getPlayer(args[0]);
                if (target.equals(commandSender.getName())){
                    commandSender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-kick-yourself").replace("{command}", "/kick <player> <reason>")));
                    return true;
                }else {
                    StringBuilder reason = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        reason.append(args[i]).append(" ");
                    }
                    ((Player) target).kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("kick-message").replace("{player}", commandSender.getName()).replace("{reason}", reason.toString())));
                }
            }
        }




        return true;
    }
}
