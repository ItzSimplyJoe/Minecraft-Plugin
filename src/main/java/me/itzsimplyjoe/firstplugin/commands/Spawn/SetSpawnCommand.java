package me.itzsimplyjoe.firstplugin.commands.Spawn;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SetSpawnCommand implements CommandExecutor {
    private final Firstplugin plugin;

    public SetSpawnCommand(Firstplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (p.hasPermission("firstplugin.setspawn")){
                Location location = p.getLocation();

               plugin.getConfig().set("spawn", location);
               plugin.saveConfig();
               p.sendMessage("§7Spawn Location set successfully!");
            }else{
                p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
            }
        }else{
            System.out.println("Only a player can run this command");
        }
        return true;
    }
}
