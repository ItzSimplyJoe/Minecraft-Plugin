package me.itzsimplyjoe.firstplugin.commands.Spawn;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final Firstplugin plugin;

    public SpawnCommand(Firstplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (p.hasPermission("firstplugin.spawn")){
                Location location = plugin.getConfig().getLocation("spawn");

                if (location != null){
                    p.teleport(location);
                    p.sendMessage("§7You Have been teleported to spawn");
                }else{
                    p.sendMessage("§7No spawn point has been set yet!");
                }
            }else{
                p.sendMessage("§cYou don't have permission for this!");
            }



        }else{
            System.out.println("Only a player can run this command");
        }
        return true;
    }
}
