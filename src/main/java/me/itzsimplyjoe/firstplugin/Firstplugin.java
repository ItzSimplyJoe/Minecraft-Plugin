package me.itzsimplyjoe.firstplugin;

import me.itzsimplyjoe.firstplugin.Listeners.OnJoin;
import me.itzsimplyjoe.firstplugin.Listeners.OnLeave;
import me.itzsimplyjoe.firstplugin.Scoreboards.OpenScoreboardCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.AdventureCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.CreativeCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.SpectatorCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.SurvivalCommand;
import me.itzsimplyjoe.firstplugin.commands.QOL.FeedCommand;
import me.itzsimplyjoe.firstplugin.commands.QOL.GodCommand;
import me.itzsimplyjoe.firstplugin.commands.QOL.RandomTPCommand;
import me.itzsimplyjoe.firstplugin.commands.Spawn.SetSpawnCommand;
import me.itzsimplyjoe.firstplugin.commands.Spawn.SpawnCommand;
import me.itzsimplyjoe.firstplugin.commands.Staff.*;
import me.itzsimplyjoe.firstplugin.utils.BanUtils;
import me.itzsimplyjoe.firstplugin.utils.TeleportUtils;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Firstplugin extends JavaPlugin {
    private static Firstplugin plugin;
    public ArrayList<Player> vanishedPlayers = new ArrayList<>();


    @Override
    public void onEnable() {
        plugin = this;
        System.out.println("Joe's Plugin has started successfully hopefully!");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new OnJoin(this), this);
        getServer().getPluginManager().registerEvents(new OnLeave(this), this);
        getCommand("c").setExecutor(new CreativeCommand(this));
        getCommand("a").setExecutor(new AdventureCommand(this));
        getCommand("s").setExecutor(new SurvivalCommand(this));
        getCommand("sp").setExecutor(new SpectatorCommand(this));
        getCommand("feed").setExecutor(new FeedCommand(this));
        getCommand("god").setExecutor(new GodCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("rtp").setExecutor(new RandomTPCommand(this));
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("sb").setExecutor(new OpenScoreboardCommand(this));
        getCommand("punish").setExecutor(new BanGUI(this));
        getCommand("unban").setExecutor(new unbanCommand(this));
        getCommand("ban").setExecutor(new BanCommand(this));
        getCommand("tempban").setExecutor(new tempbanCommand(this));
        getCommand("kick").setExecutor(new kickCommand(this));
        TeleportUtils utils = new TeleportUtils(this);
        new BanUtils(plugin);
    }
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        BanEntry banEntry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(player.getName());
        if (banEntry != null) {
            String banMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("BanMessage").replace("{player}", player.getName()).replace("{reason}", banEntry.getReason()).replace("{time}", banEntry.getExpiration().toString()));
            event.setKickMessage(banMessage);
        }
    }
    @Override
    public void onDisable() {
        System.out.println("Joe's Plugin has stopped successfully hopefully!");
    }

    public static Firstplugin getPlugin(){
        return plugin;
    }


}
