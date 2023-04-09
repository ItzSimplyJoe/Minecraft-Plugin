package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class VanishCommand implements CommandExecutor {
    static Firstplugin plugin;

    public VanishCommand(Firstplugin plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (args.length == 0) {
                if (!commandSender.hasPermission("firstplugin.vanish")) {
                    p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
                    return true;
                } else {
                    Player player = (Player) commandSender;
                    if (plugin.vanishedPlayers.contains(player)) {
                        for (Player people : Bukkit.getOnlinePlayers()) {
                            people.showPlayer(plugin, player);
                        }
                        plugin.vanishedPlayers.remove(player);
                        player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("unvanish-success-message")));
                    }else if(!plugin.vanishedPlayers.contains(player)){
                        for (Player people : Bukkit.getOnlinePlayers()) {
                            people.hidePlayer(plugin, player);
                        }
                        plugin.vanishedPlayers.add(player);
                        player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("vanish-success-message")));
                    }
                }
            } else {
                String target = args[0];
                if (!commandSender.hasPermission("firstplugin.vanish.others")) {
                    p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
                    return true;
                } else {
                    Player playerName = Bukkit.getServer().getPlayerExact(target);
                    if (playerName == null) {
                        p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-player-not-found")));
                    } else {
                        if (plugin.vanishedPlayers.contains(playerName)) {
                            for (Player people : Bukkit.getOnlinePlayers()) {
                                people.showPlayer(plugin, playerName);
                            }
                            plugin.vanishedPlayers.remove(playerName);
                            playerName.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("unvanish-success-message")));
                        }else if(!plugin.vanishedPlayers.contains(playerName)){
                            for (Player people : Bukkit.getOnlinePlayers()) {
                                people.hidePlayer(plugin, playerName);
                            }
                            plugin.vanishedPlayers.add(playerName);
                            playerName.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("vanish-success-message")));
                        }
                    }
                }
            }

        } else {
            System.out.println("You must be a player to use this command!");
        }
        return true;
    }
}

