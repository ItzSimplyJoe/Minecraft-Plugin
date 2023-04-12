package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.BanUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {
    private final Firstplugin plugin;

    public BanCommand(Firstplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;
            if (player.hasPermission("firstplugin.ban")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-ban").replace("{command}", "/ban <player> <reason>")));
                } else if (args.length == 1) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-time-for-ban").replace("{command}", "/ban <player> <time> <reason>")));
                } else if (args.length >= 2) {
                    Player target = player.getServer().getPlayer(args[0]);
                    if (target.equals(player.getName())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-ban-yourself")));
                        return true;
                    } else {
                        StringBuilder reason = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            reason.append(args[i]).append(" ");
                        }
                        BanUtils.banPlayer(target, reason.toString(), -1);
                        Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ban-message").replace("{player}", target.getName()).replace("{reason}", reason.toString())), "firstplugin.ban");
                    }
                }
            }
        }else{
            if (args.length == 0) {
                System.out.println((ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-ban").replace("{command}", "/ban <player> <reason>"))));
            } else if (args.length == 1) {
                System.out.println((ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-reason-for-ban").replace("{command}", "/ban <player> <reason>"))));
            } else if (args.length >= 2) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                BanUtils.banPlayer(target, reason.toString(), -1);
                Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ban-message").replace("{player}", target.getName()).replace("{reason}", reason.toString())), "firstplugin.ban");
            }
        }
        return true;
    }
}
