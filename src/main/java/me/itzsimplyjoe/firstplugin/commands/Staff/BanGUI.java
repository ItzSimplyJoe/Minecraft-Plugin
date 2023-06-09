package me.itzsimplyjoe.firstplugin.commands.Staff;

import me.itzsimplyjoe.firstplugin.utils.BanUtils;
import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.MuteUtils;
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

import java.util.ArrayList;
import java.util.List;

public class BanGUI implements CommandExecutor, Listener {
    private final Firstplugin plugin;
    private Player playerBeingPunished;
    private MuteUtils config;

    public BanGUI(Firstplugin plugin) {
        this.plugin = plugin;
        this.config = config;
        Bukkit.getPluginManager().registerEvents((Listener) new BanUtils(plugin), plugin);
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

        if (((Player) sender).getUniqueId().equals(playerBeingPunished.getUniqueId())) {
            sender.sendMessage("You cannot ban yourself.");
            return true;
        }

        Inventory inventory = Bukkit.createInventory(null, plugin.getConfig().getInt("GUI.size"), plugin.getConfig().getString("GUI.name") + playerBeingPunished.getName());
        for (String key : plugin.getConfig().getConfigurationSection("GUI.items").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("GUI.items." + key + ".material")));
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(plugin.getConfig().getString("GUI.items." + key + ".type"));
            lore.add(plugin.getConfig().getString("GUI.items." + key + ".length-of-punishment"));
            meta.setLore(lore);
            meta.setDisplayName(plugin.getConfig().getString("GUI.items." + key + ".reason"));
            item.setItemMeta(meta);
            inventory.setItem(plugin.getConfig().getInt("GUI.items." + key + ".slot"), item);
        }
        ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("GUI.cancel.material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Cancel");
        item.setItemMeta(meta);
        inventory.setItem(plugin.getConfig().getInt("GUI.cancel.slot"), item);
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

        List<String> lore = event.getCurrentItem().getItemMeta().getLore();
        String type = lore.get(0);
        String lengthLine = lore.get(1);
        if (type.equals("ban")) {
            int lengthinsec = BanUtils.getLength(lengthLine);
            String reason = event.getCurrentItem().getItemMeta().getDisplayName();
            BanUtils.banPlayer(playerBeingPunished, reason, lengthinsec);
            event.getWhoClicked().closeInventory();
        }else if (type.equals("kick")) {
            String reason = event.getCurrentItem().getItemMeta().getDisplayName();
            BanUtils.kickPlayer(playerBeingPunished, reason);
            event.getWhoClicked().closeInventory();
        }else if (type.equals("mute")) {
            String reason = event.getCurrentItem().getItemMeta().getDisplayName();
            config.mutePlayer(playerBeingPunished);
            event.getWhoClicked().closeInventory();
        }else if (type.equals("tempmute")) {
            int lengthinsec = BanUtils.getLength(lengthLine);
            lengthinsec = lengthinsec * 1000;
            String reason = event.getCurrentItem().getItemMeta().getDisplayName();
            config.tempMutePlayer(playerBeingPunished, lengthinsec);
            event.getWhoClicked().closeInventory();
        }
    }
}


