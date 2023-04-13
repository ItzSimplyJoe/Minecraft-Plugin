package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.MuteUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand implements CommandExecutor {
        private Firstplugin plugin;
        public UnmuteCommand(Firstplugin plugin) {

            this.plugin = plugin;
        }
        private MuteUtils config;

        public UnmuteCommand(MuteUtils config) {
            this.config = config;
        }
        @Override
        public boolean onCommand (CommandSender commandSender, Command command, String s, String[]args){

            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (player.hasPermission("firstplugin.unmute")) {
                    if (args.length == 0) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-unmute").replace("{command}", "/unmute <player> <reason>")));
                    } else if (args.length >= 1) {
                        Player target = player.getServer().getPlayer(args[0]);
                        config.unmutePlayer(target);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-unmuted-message").replace("{command}", "/unmute <player>").replace("{player}", target.getName())));
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("unmuted-message").replace("{command}", "/unmute <player>").replace("{player}", player.getName())));
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-permission").replace("{command}", "/unmute <player>")));
                }
            } else {
                if (args.length == 0) {
                    System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-player-for-unmute").replace("{command}", "/unmute <player>")));
                } else if (args.length == 1) {
                    System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("error-message-no-reason-for-unmute").replace("{command}", "/unmute <player>")));
                } else if (args.length >= 2) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    config.unmutePlayer(target);
                    System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-unmuted-message").replace("{command}", "/unmute <player>").replace("{player}", target.getName())));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("unmuted-message").replace("{command}", "/unmute <player>").replace("{player}", "CONSOLE")));
                }
            }


            return true;
        }
    }



