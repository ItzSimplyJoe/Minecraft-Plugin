package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.BanUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tempbanCommand implements CommandExecutor {
    private final Firstplugin plugin;
    public tempbanCommand(Firstplugin plugin) {

        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (player.hasPermission("firstplugin.tempban")){
            if (args.length == 0){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-ban").replace("{command}", "/ban <player> <time> <reason>")));
            }else if(args.length == 1) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-time-for-ban").replace("{command}", "/ban <player> <time> <reason>")));
            }else if(args.length == 2){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-reason-for-ban").replace("{command}", "/ban <player> <time> <reason>")));
            }else if(args.length >= 3){
                Player target = player.getServer().getPlayer(args[0]);
                if (target.equals(player.getName())){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-ban-yourself")));
                    return true;
                }else {
                    StringBuilder reason = new StringBuilder();
                    int length = BanUtils.getLength(args[1]);
                    for (int i = 2; i < args.length; i++) {
                        reason.append(args[i]).append(" ");
                    }
                    BanUtils.banPlayer(target, reason.toString(), length);
                    Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ban-message").replace("{player}", target.getName()).replace("{reason}", reason.toString())), "firstplugin.tempban");
                }
            }
        }
        return true;
    }
}
