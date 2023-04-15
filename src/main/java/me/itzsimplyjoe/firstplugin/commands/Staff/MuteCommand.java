package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.MuteUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MuteCommand implements CommandExecutor {
    private Firstplugin plugin;
    public MuteCommand(Firstplugin plugin) {

        this.plugin = plugin;
    }
    private MuteUtils config;

    public MuteCommand(MuteUtils config) {
        this.config = config;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.hasPermission("firstplugin.mute")){
                if (args.length == 0){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-mute").replace("{command}", "/mute <player> <reason>")));
                } else if (args.length == 1){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-reason-for-mute").replace("{command}", "/mute <player> <reason>")));
                } else if (args.length >= 2){
                    Player target = player.getServer().getPlayer(args[0]);
                    config.mutePlayer(target);
                    StringBuilder reason = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        reason.append(args[i]).append(" ");
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-muted-message").replace("{command}", "/mute <player> <reason>").replace("{player}", target.getName()).replace("{reason}", reason.toString())));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("muted-message").replace("{command}", "/mute <player> <reason>").replace("{player}", player.getName()).replace("{reason}", reason.toString())));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-permission").replace("{command}", "/mute <player> <reason>")));
            }
        } else {
            if (args.length == 0) {
                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-mute").replace("{command}", "/mute <player> <reason>")));
            } else if (args.length == 1) {
                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-reason-for-mute").replace("{command}", "/mute <player> <reason>")));
            } else if (args.length >= 2) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                config.mutePlayer(target);
                StringBuilder reason = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-muted-message").replace("{command}", "/mute <player> <reason>").replace("{player}", target.getName()).replace("{reason}", reason.toString())));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("muted-message").replace("{command}", "/mute <player> <reason>").replace("{player}", "CONSOLE").replace("{reason}", reason.toString())));
            }
        }



        return true;
    }
}



