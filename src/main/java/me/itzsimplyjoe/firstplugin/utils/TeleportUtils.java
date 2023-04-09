package me.itzsimplyjoe.firstplugin.utils;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;

public class TeleportUtils {

    static Firstplugin plugin;

    public TeleportUtils(Firstplugin plugin){
        this.plugin = plugin;
    }
    public static HashSet<Material> bad_blocks = new HashSet<>();

    static{
        bad_blocks.add(Material.LAVA);
        bad_blocks.add(Material.CACTUS);
        bad_blocks.add(Material.FIRE);
        bad_blocks.add(Material.WATER);
        bad_blocks.add(Material.CAMPFIRE);
        bad_blocks.add(Material.SOUL_FIRE);
        bad_blocks.add(Material.SOUL_CAMPFIRE);
        bad_blocks.add(Material.MAGMA_BLOCK);
        bad_blocks.add(Material.NETHER_PORTAL);
        bad_blocks.add(Material.END_PORTAL);
        bad_blocks.add(Material.END_PORTAL_FRAME);
        bad_blocks.add(Material.END_GATEWAY);
        bad_blocks.add(Material.SWEET_BERRY_BUSH);
    }
    public static Location generateLocation(Player player){
        Random random = new Random();

        int x = random.nextInt(plugin.getConfig().getInt("border"));
        int z = random.nextInt(plugin.getConfig().getInt("border"));

        if (plugin.getConfig().getBoolean("world-border")){
            x = random.nextInt(plugin.getConfig().getInt("border"));
            z = random.nextInt(plugin.getConfig().getInt("border"));
        }else if (!plugin.getConfig().getBoolean("world-border")){
            x = random.nextInt(25000);
            z = random.nextInt(25000);
        }
        int y = player.getWorld().getHighestBlockYAt(x, z);
        Location loc = new Location(player.getWorld(), x, y, z);
        while(!isLocationSafe(loc)){
            loc = generateLocation(player);
        }
        return loc;
    }

    public static boolean isLocationSafe(Location loc){
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        Block block = loc.getWorld().getBlockAt(x, y, z);
        Block above = loc.getWorld().getBlockAt(x, y + 1, z);
        Block below = loc.getWorld().getBlockAt(x, y - 1, z);

        return !(bad_blocks.contains(below.getType()) || block.getType().isSolid() || above.getType().isSolid());
    }
}
