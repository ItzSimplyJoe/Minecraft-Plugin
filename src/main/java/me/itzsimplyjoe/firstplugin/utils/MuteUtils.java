package me.itzsimplyjoe.firstplugin.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;

public class MuteUtils {

    private Set<Player> mutedPlayers;

    public void MuteSystemConfig() {
        mutedPlayers = new HashSet<>();
    }
    public void mutePlayer(Player player) {
        mutedPlayers.add(player);
    }

    public void tempMutePlayer(Player player, long durationMillis) {
        mutedPlayers.add(player);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mutedPlayers.remove(player);
            }
        }, durationMillis);
    }

    public void unmutePlayer(Player player) {
        mutedPlayers.remove(player);
    }

    public boolean isMuted(Player player) {

        return mutedPlayers.contains(player);
    }

}
