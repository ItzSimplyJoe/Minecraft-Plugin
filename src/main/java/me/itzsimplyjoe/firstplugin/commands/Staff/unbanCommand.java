package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class unbanCommand implements CommandExecutor {
    private final Firstplugin plugin;

    public unbanCommand(Firstplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            String message = plugin.getConfig().getString("error-message-no-args");
            message = message.replace("{command}", "/unban");
            commandSender.sendMessage(message);
            return true;
        }
        if (commandSender.hasPermission("firstplugin.unban")) {
            if (Bukkit.getServer().getBanList(BanList.Type.NAME).isBanned(args[0])) {
                Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(args[0]);
                String message = plugin.getConfig().getString("unban-message");
                message = message.replace("{player}", args[0]);
                commandSender.sendMessage(message);
            } else {
                String message = plugin.getConfig().getString("player-not-banned");
                message = message.replace("{player}", args[0]);
                commandSender.sendMessage(message);
            }
        } else {
            commandSender.sendMessage(plugin.getConfig().getString("error-message-no-permission"));
        }
        return true;
    }
}
