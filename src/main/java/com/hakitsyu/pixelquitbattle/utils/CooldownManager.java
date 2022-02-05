package com.hakitsyu.pixelquitbattle.utils;

import org.spongepowered.api.entity.living.player.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CooldownManager {

    private static int DEFAULT_TIME = 1500;

    private Map<Player, Date> data;
    private int time;

    public CooldownManager(int time) {
        this.data = new HashMap<>();
        this.time = time;
    }

    public CooldownManager() {
        this.data = new HashMap<>();
        this.time = DEFAULT_TIME;
    }

    public void apply(Player player) {
        data.put(player, new Date());
    }

    public void remove(Player player) {
        if (data.containsKey(player))
            data.remove(player);
    }

    // Check if contains in data
    public boolean contains(Player player) {
        return data.containsKey(player);
    }

    // Check if end the cooldown
    public boolean ended(Player player) {
        return dateDifference(player) >= time;
    }

    // Return in milliseconds time to end cooldown
    public long timeToEnd(Player player) {
        return time - dateDifference(player);
    }

    // Return difference of now date and old date
    private long dateDifference(Player player) {
        Date old = getDate(player);
        Date now = new Date();

        return now.getTime() - old.getTime();
    }

    public Date getDate(Player player) {
        return contains(player) ? data.get(player) : null;
    }

    public Map<Player, Date> getData() {
        return data;
    }

    public int getTime() {
        return time;
    }

}
