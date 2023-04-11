package me.itzsimplyjoe.firstplugin.utils;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Locale;

public class BanUtils {
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

}
