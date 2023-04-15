package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.BanUtils;
import me.itzsimplyjoe.firstplugin.utils.MuteUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempMuteCommand implements CommandExecutor {
    private Firstplugin plugin;
    public TempMuteCommand(Firstplugin plugin) {

        this.plugin = plugin;
    }
    private MuteUtils config;

    public TempMuteCommand(MuteUtils config) {
        this.config = config;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.hasPermission("firstplugin.tempmute")){
                if (args.length == 0){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-mute").replace("{command}", "/tempmute <player> <time> <reason>")));
                } else if (args.length == 1){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-reason-for-mute").replace("{command}", "/tempmute <player> <time> <reason>")));
                } else if (args.length == 2){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-time-for-mute").replace("{command}", "/tempmute <player> <time> <reason>")));
                } else if (args.length >= 3){
                    Player target = player.getServer().getPlayer(args[0]);
                    int time = BanUtils.getLength(args[1]);
                    time = time * 1000;
                    config.tempMutePlayer(target, time);
                    StringBuilder reason = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        reason.append(args[i]).append(" ");
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-muted-message").replace("{command}", "/tempmute <player> <time> <reason>").replace("{player}", target.getName()).replace("{reason}", reason.toString())));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("muted-message").replace("{command}", "/tempmute <player> <time> <reason>").replace("{player}", player.getName()).replace("{reason}", reason.toString())));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-permission").replace("{command}", "/tempmute <player> <time> <reason>")));
            }
        } else {
            if (args.length == 0) {
                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-mute").replace("{command}", "/tempmute <player> <time> <reason>")));
            } else if (args.length == 1) {
                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-reason-for-mute").replace("{command}", "/tempmute <player> <time> <reason>")));
            } else if (args.length >= 2) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                int time = BanUtils.getLength(args[1]);
                time = time * 1000;
                config.tempMutePlayer(target, time);
                StringBuilder reason = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-muted-message").replace("{command}", "/tempmute <player> <time> <reason>").replace("{player}", target.getName()).replace("{reason}", reason.toString())));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("muted-message").replace("{command}", "/tempmute <player> <time> <reason>").replace("{player}", "CONSOLE").replace("{reason}", reason.toString())));
            }
        }
        return true;
    }
}

