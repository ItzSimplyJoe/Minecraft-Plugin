package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.utils.BanUtils;
import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class BanGUI implements CommandExecutor, Listener {
    private final Firstplugin plugin;
    private Player playerBeingPunished;

    public BanGUI(Firstplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("punish")) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        if (args.length == 0) {
            String message = plugin.getConfig().getString("error-message-no-args");
            message = message.replace("{command}", "/punish <player>");
            sender.sendMessage(message);
            return true;
        }

        playerBeingPunished = Bukkit.getPlayer(args[0]);
        if (playerBeingPunished == null) {
            String message = plugin.getConfig().getString("player-not-found");
            message = message.replace("{player}", args[0]);
            sender.sendMessage(message);
            return true;
        }

//        if (((Player) sender).getUniqueId().equals(playerBeingPunished.getUniqueId())) {
//            sender.sendMessage("You cannot ban yourself.");
//            return true;
//        }

        Inventory inventory = Bukkit.createInventory(null, plugin.getConfig().getInt("GUI.size"), plugin.getConfig().getString("GUI.name") + playerBeingPunished.getName());
        for (String key : plugin.getConfig().getConfigurationSection("GUI.items").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("GUI.items." + key + ".material")));
            ItemMeta meta = item.getItemMeta();
            meta.setLore(Arrays.asList(plugin.getConfig().getString("GUI.items." + key + ".length-of-ban")));
            meta.setDisplayName(plugin.getConfig().getString("GUI.items." + key + ".reason"));
            item.setItemMeta(meta);
            inventory.setItem(plugin.getConfig().getInt("GUI.items." + key + ".slot"), item);
        }
        ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("GUI.cancel.material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Cancel");
        item.setItemMeta(meta);
        inventory.setItem(plugin.getConfig().getInt("GUI.cancel.slot"), item);
        ItemStack item2 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("GUI.custom-ban.material")));
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("Custom Ban");
        item2.setItemMeta(meta2);
        inventory.setItem(plugin.getConfig().getInt("GUI.custom-ban.slot"), item2);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        ((Player) sender).openInventory(inventory);

        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().startsWith(plugin.getConfig().getString("GUI.name"))) {
            return;
        }

        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Cancel")) {
            event.getWhoClicked().closeInventory();
            return;
        }
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Custom Ban")) {
            event.getWhoClicked().closeInventory();
            BanUtils.openAnvilGUI((Player) event.getWhoClicked(),null, "Enter custom ban reason:");
            return;
        }

        List<String> lore = event.getCurrentItem().getItemMeta().getLore();
        String lengthLine = lore.get(0);
        int lengthinsec =  BanUtils.getLength(lengthLine);
        String reason = event.getCurrentItem().getItemMeta().getDisplayName();
        BanUtils.banPlayer(playerBeingPunished, reason, lengthinsec);
        event.getWhoClicked().closeInventory();
    }
}


