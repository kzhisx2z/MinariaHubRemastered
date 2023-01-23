package fr.kx2zh.minaria.hub.module;

import fr.kx2zh.minaria.hub.Hub;
import fr.kx2zh.minaria.hub.cooldown.CooldownManager;
import fr.kx2zh.minaria.hub.cooldown.CooldownType;
import org.bukkit.event.Listener;

import java.util.UUID;

public abstract class Module implements Listener {

    protected final Hub hub;
    private final ModuleType moduleType;

    private final CooldownManager cooldownManager;


    public Module(Hub hub, ModuleType moduleType) {
        this.hub = hub;
        this.moduleType = moduleType;

        cooldownManager = hub.getCooldownManager();
    }

    protected boolean tryCooldown(UUID uuid, CooldownType key, long delay) {
        return cooldownManager.tryCooldown(uuid, key, delay);
    }

    protected long getCooldown(UUID uuid, CooldownType key) {
        return cooldownManager.getCooldown(uuid, key);
    }

    public Hub getHub() {
        return hub;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public abstract void onEnable();
    public abstract void onDisable();
}
