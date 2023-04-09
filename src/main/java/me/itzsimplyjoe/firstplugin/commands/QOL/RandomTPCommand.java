package me.itzsimplyjoe.firstplugin.commands.QOL;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RandomTPCommand implements CommandExecutor {
    static Firstplugin plugin;

    public RandomTPCommand(Firstplugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (args.length == 0){
                if (!commandSender.hasPermission("firstplugin.randomtp")) {
                    p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
                    return true;
                }else {
                    Player player = (Player) commandSender;
                    player.teleport(TeleportUtils.generateLocation(player));
                    player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("randomtp-success-message")));
                }
            }else {
                String target = args[0];
                if (!commandSender.hasPermission("firstplugin.randomtp.others")) {
                    p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
                    return true;
                } else {
                    Player playerName = Bukkit.getServer().getPlayerExact(target);
                    if (playerName == null) {
                        p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-player-not-found")));
                    } else {
                        playerName.teleport(TeleportUtils.generateLocation(playerName));
                        playerName.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("randomtp-success-message")));

                    }
                }
            }

        } else {
            System.out.println("You must be a player to use this command!");
        }





        return true;
    }
}
