package me.itzsimplyjoe.firstplugin.utils;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;
import java.util.Locale;

public class BanUtils {
    private final Firstplugin plugin;

    public BanUtils(Firstplugin plugin) {
        this.plugin = plugin;
    }
    private static Inventory anvilInventory;
    private static String reasoncheck;
    private static ItemStack paper;
    public static int getLength(String length) {
        length = length.toLowerCase(Locale.ROOT);
        if (length.contains("perm")) {
            return -1;
        } else if (length.contains("d")) {
            return Integer.parseInt(length.replace("d", "")) * 60 * 60 * 24;
        } else if (length.contains("h")) {
            return Integer.parseInt(length.replace("h", "")) * 60 * 60;
        } else if (length.contains("m")) {
            return Integer.parseInt(length.replace("m", "")) * 60;
        } else if (length.contains("s")) {
            return Integer.parseInt(length.replace("s", ""));
        } else {
            return 0;
        }
    }

    public static void banPlayer(Player player, String reason, int length) {
        if (length == -1) { // permanent ban
            Bukkit.getServer().banIP(player.getAddress().getAddress().getHostAddress());
            player.kickPlayer(reason);
        } else {
            long expires = System.currentTimeMillis() + (length * 1000L);
            Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(player.getName(), reason, new Date(expires), null);
            player.kickPlayer(reason);
        }
    }

    public static void openAnvilGUI(Player player, String reasoncheck, String title) {
        anvilInventory = Bukkit.createInventory(player, InventoryType.ANVIL, title);
        paper = new ItemStack(Material.PAPER);
        anvilInventory.setItem(0, paper);
        player.openInventory(anvilInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(anvilInventory)) {
            AnvilInventory anvil = (AnvilInventory) event.getClickedInventory();
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack result = anvil.getItem(2);
                ItemStack previous = event.getCurrentItem();

                if (result != null && previous != null && result.hasItemMeta() && previous.hasItemMeta()) {
                    ItemMeta resultMeta = result.getItemMeta();
                    ItemMeta previousMeta = previous.getItemMeta();

                    if (!resultMeta.getDisplayName().equals(previousMeta.getDisplayName())) {
                        Player player = (Player) event.getWhoClicked();
                        String newName = resultMeta.getDisplayName();
                        if (reasoncheck==null){
                            openAnvilGUI(player, newName, "Enter a length of time");
                        }else{
                            int length = getLength(newName);
                            banPlayer(player, reasoncheck, length);
                        }
                        player.closeInventory();
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(anvilInventory)) {
            Player player = (Player) event.getPlayer();
            anvilInventory = null;
            player.getInventory().addItem(paper);
        }
    }
}
