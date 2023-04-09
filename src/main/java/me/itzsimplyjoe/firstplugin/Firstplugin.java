package me.itzsimplyjoe.firstplugin;

import me.itzsimplyjoe.firstplugin.Listeners.BanInventoryListener;
import me.itzsimplyjoe.firstplugin.Listeners.OnJoin;
import me.itzsimplyjoe.firstplugin.Listeners.OnLeave;
import me.itzsimplyjoe.firstplugin.commands.BanGUICommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.AdventureCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.CreativeCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.SpectatorCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.SurvivalCommand;
import me.itzsimplyjoe.firstplugin.commands.QOL.FeedCommand;
import me.itzsimplyjoe.firstplugin.commands.QOL.GodCommand;
import me.itzsimplyjoe.firstplugin.commands.RandomTPCommand;
import me.itzsimplyjoe.firstplugin.commands.Spawn.SetSpawnCommand;
import me.itzsimplyjoe.firstplugin.commands.Spawn.SpawnCommand;
import me.itzsimplyjoe.firstplugin.utils.TeleportUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Firstplugin extends JavaPlugin {
    private static Firstplugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        System.out.println("Joe's Plugin has started successfully hopefully!");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new OnJoin(this), this);
        getServer().getPluginManager().registerEvents(new OnLeave(), this);
        getServer().getPluginManager().registerEvents(new BanInventoryListener(), this);
        getCommand("c").setExecutor(new CreativeCommand(this));
        getCommand("a").setExecutor(new AdventureCommand(this));
        getCommand("s").setExecutor(new SurvivalCommand(this));
        getCommand("sp").setExecutor(new SpectatorCommand(this));
        getCommand("feed").setExecutor(new FeedCommand(this));
        getCommand("god").setExecutor(new GodCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("rtp").setExecutor(new RandomTPCommand(this));
        getCommand("bangui").setExecutor(new BanGUICommand(this));
        TeleportUtils utils = new TeleportUtils(this);
    }

    @Override
    public void onDisable() {
        System.out.println("Joe's Plugin has stopped successfully hopefully!");
    }
    public static Firstplugin getPlugin(){
        return plugin;
    }


}
