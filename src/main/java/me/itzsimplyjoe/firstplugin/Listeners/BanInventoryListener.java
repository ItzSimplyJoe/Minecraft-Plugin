package me.itzsimplyjoe.firstplugin.Listeners;

import me.itzsimplyjoe.firstplugin.Firstplugin;
import me.itzsimplyjoe.firstplugin.utils.BanMenuUtils;
import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BanInventoryListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        //Check inventory
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLUE + "Player List")){
            //make sure they clicked on a player head
            if (e.getCurrentItem().getType() == Material.PLAYER_HEAD){

                //Get player they clicked on from item name
                Player whoToBan = Firstplugin.getPlugin().getServer().getPlayerExact(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));

                BanMenuUtils.openPlayerMenu(player, whoToBan);
            }

        }else if(e.getView().getTitle().equalsIgnoreCase("Ban This Noob")){
            switch(e.getCurrentItem().getType()){
                case BARRIER:
                    BanMenuUtils.openBanMenu(player);
                    break;
                case WOODEN_AXE:
                    //Get name
                    String name = e.getClickedInventory().getItem(4).getItemMeta().getDisplayName();
                    player.getServer().getBanList(BanList.Type.NAME).addBan(ChatColor.stripColor(name), "I said so", null, "The code god");
                    player.sendMessage(ChatColor.GREEN + "Banned Player");
                    break;
            }
        }
        //make it so they cant move items
        e.setCancelled(true);
    }

}

