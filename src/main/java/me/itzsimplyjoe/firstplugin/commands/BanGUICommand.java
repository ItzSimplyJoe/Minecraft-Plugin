package me.itzsimplyjoe.firstplugin.commands;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.BanMenuUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BanGUICommand implements CommandExecutor {
    static Firstplugin plugin;

    public BanGUICommand(Firstplugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (p.hasPermission("firstplugin.ban")) {
                BanMenuUtils.openBanMenu(p);
            }else{
                p.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("error-message-no-permission")));
            }
        }

        return true;
    }

}

