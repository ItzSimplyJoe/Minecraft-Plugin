package me.itzsimplyjoe.firstplugin.commands.Gamemodes;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CreativeCommand implements CommandExecutor {

    static Firstplugin plugin;

    public CreativeCommand(Firstplugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            if (args.length == 0){
                Player p = (Player) commandSender;
                if (p.hasPermission("firstplugin.creative")) {
                    if (p.getGameMode() == GameMode.CREATIVE) {
                        p.sendMessage("§cYou are already in creative");
                    } else {
                        p.setGameMode(GameMode.CREATIVE);
                    }
                }else{
                    p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
                }
            }else{
                String playerName = args[0];
                Player target = Bukkit.getServer().getPlayerExact(playerName);
                Player p = (Player) commandSender;
                if (p.hasPermission("firstplugin.creative.others")){
                    if (target == null){
                        p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-player-not-found")));
                    }else{
                        if (target.getGameMode() == GameMode.CREATIVE) {
                            p.sendMessage("§cThis player is already in creative");
                        } else {
                            target.setGameMode(GameMode.CREATIVE);
                        }
                    }
                }else{
                    p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
                }
            }
        }else{
            System.out.println("Only a player can run this command");
        }
        return true;
    }
}
