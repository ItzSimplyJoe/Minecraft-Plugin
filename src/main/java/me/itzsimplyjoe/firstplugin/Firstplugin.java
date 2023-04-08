package me.itzsimplyjoe.firstplugin;

import me.itzsimplyjoe.firstplugin.Listeners.OnJoin;
import me.itzsimplyjoe.firstplugin.Listeners.OnLeave;
import me.itzsimplyjoe.firstplugin.commands.*;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.AdventureCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.CreativeCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.SpectatorCommand;
import me.itzsimplyjoe.firstplugin.commands.Gamemodes.SurvivalCommand;
import me.itzsimplyjoe.firstplugin.commands.Spawn.SetSpawnCommand;
import me.itzsimplyjoe.firstplugin.commands.Spawn.SpawnCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Firstplugin extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Joe's Plugin has started successfully hopefully!");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new OnJoin(this), this);
        getServer().getPluginManager().registerEvents(new OnLeave(), this);
        getCommand("c").setExecutor(new CreativeCommand());
        getCommand("a").setExecutor(new AdventureCommand());
        getCommand("s").setExecutor(new SurvivalCommand());
        getCommand("sp").setExecutor(new SpectatorCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("god").setExecutor(new GodCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
    }

    @Override
    public void onDisable() {
        System.out.println("Joe's Plugin has stopped successfully hopefully!");
    }

}
