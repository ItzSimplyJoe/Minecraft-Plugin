package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.Firstplugin;
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
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-mute").replace("{command}", "/mute <player> <reason>")));
                } else if (args.length == 1){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-reason-for-mute").replace("{command}", "/mute <player> <reason>")));
                } else if (args.length >= 2){
                    Player target = player.getServer().getPlayer(args[0]);
                    config.mutePlayer(target);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-muted-message").replace("{command}", "/mute <player> <reason>").replace("{player}", target.getName()).replace("{reason}", args[1])));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("muted-message").replace("{command}", "/mute <player> <reason>").replace("{player}", player.getName()).replace("{reason}", args[1])));
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
                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-muted-message").replace("{command}", "/mute <player> <reason>").replace("{player}", target.getName()).replace("{reason}", args[1])));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("muted-message").replace("{command}", "/mute <player> <reason>").replace("{player}", "CONSOLE").replace("{reason}", args[1])));
            }
        }
        return true;
    }
}
