package fr.kx2zh.minaria.hub.cooldown;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.UUID;

public class CooldownManager {

    private final Table<UUID, CooldownType, Long> cooldowns = HashBasedTable.create();

    public long getCooldown(UUID uuid, CooldownType key) {
        return calculateRemainder(cooldowns.get(uuid, key));
    }

    public long setCooldown(UUID uuid, CooldownType key, long delay) {
        return calculateRemainder(cooldowns.put(uuid, key, System.currentTimeMillis() + (delay * 1000)));
    }

    public boolean tryCooldown(UUID uuid, CooldownType key, long delay) {
        if (getCooldown(uuid, key) / 1000 > 0) return false;
        setCooldown(uuid, key, delay + 1);
        return true;
    }

    public void removeCooldown(UUID uuid) {
        cooldowns.row(uuid).clear();
    }

    private long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
    }
}
