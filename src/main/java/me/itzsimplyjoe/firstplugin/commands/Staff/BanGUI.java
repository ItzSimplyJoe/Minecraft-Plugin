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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BanGUI implements CommandExecutor {
    static Firstplugin plugin;
    private String[] args;

    public BanGUI(Firstplugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("punish")) {
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
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                String message = plugin.getConfig().getString("player-not-found");
                message = message.replace("{player}", args[0]);
                sender.sendMessage(message);
                return true;
            }
            this.args = args;
            Inventory inventory = Bukkit.createInventory(null, plugin.getConfig().getInt("GUI.size"), plugin.getConfig().getString("GUI.name") + player.getName());
            for (String key : plugin.getConfig().getConfigurationSection("GUI.items").getKeys(false)) {
                ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("GUI.items." + key + ".material")));
                ItemMeta meta = item.getItemMeta();
                meta.setLore(plugin.getConfig().getStringList("GUI.items." + key + ".length-of-ban"));
                meta.setDisplayName(plugin.getConfig().getString("GUI.items." + key + ".reason"));
                item.setItemMeta(meta);
                inventory.setItem(plugin.getConfig().getInt("GUI.items." + key + ".slot"), item);
            }
            ((Player) sender).openInventory(inventory);
        }
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().startsWith(plugin.getConfig().getString("GUI.name"))) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            String reason = event.getCurrentItem().getItemMeta().getDisplayName();
            String length = event.getCurrentItem().getItemMeta().getLore().toString();
            int lengthinsec = 0;
            lengthinsec = BanUtils.getLength(length);
            Player player = Bukkit.getPlayer(args[0]);
            BanUtils.banPlayer(player, reason, lengthinsec);
            event.getWhoClicked().closeInventory();
        }
    }

}

