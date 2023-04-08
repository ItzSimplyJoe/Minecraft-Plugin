package me.itzsimplyjoe.firstplugin;

import me.itzsimplyjoe.firstplugin.Listeners.OnJoin;
import me.itzsimplyjoe.firstplugin.Listeners.OnLeave;
import me.itzsimplyjoe.firstplugin.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Firstplugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Joe's Plugin has started successfully hopefully!");
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnLeave(), this);
        getCommand("c").setExecutor(new CreativeCommand());
        getCommand("a").setExecutor(new AdventureCommand());
        getCommand("s").setExecutor(new SurvivalCommand());
        getCommand("sp").setExecutor(new SpectatorCommand());
        getCommand("feed").setExecutor(new FeedCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Joe's Plugin has stopped successfully hopefully!");
    }

}
