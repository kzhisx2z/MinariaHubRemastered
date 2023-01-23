package fr.kx2zh.minaria.hub;

import fr.kx2zh.minaria.hub.common.managers.EventBus;
import fr.kx2zh.minaria.hub.common.players.PlayerManager;
import fr.kx2zh.minaria.hub.cooldown.CooldownManager;
import fr.kx2zh.minaria.hub.gui.GuiManager;
import fr.kx2zh.minaria.hub.listeners.protection.EntityEditionListener;
import fr.kx2zh.minaria.hub.listeners.protection.PlayerProtectionListener;
import fr.kx2zh.minaria.hub.listeners.protection.WorldEditionListener;
import fr.kx2zh.minaria.hub.module.Module;
import fr.kx2zh.minaria.hub.module.ModuleManager;
import fr.kx2zh.minaria.hub.scoreboards.ScoreboardManager;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Hub extends JavaPlugin {
    private World world;

    private EventBus eventBus;
    private PlayerManager playerManager;
    private ScoreboardManager scoreboardManager;
    private GuiManager guiManager;
    private CooldownManager cooldownManager;
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        world = getServer().getWorlds().get(0);
        world.setGameRuleValue("randomTickSpeed", "0");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setTime(6000L);

        eventBus = new EventBus();
        playerManager = new PlayerManager(this);
        scoreboardManager = new ScoreboardManager(this);
        guiManager = new GuiManager(this);
        cooldownManager = new CooldownManager();
        moduleManager = new ModuleManager();

        moduleManager.loadModules(this);

        moduleManager.getModules().values().forEach(Module::onEnable);

        final EntityEditionListener entityEditionListener = new EntityEditionListener();
        final PlayerProtectionListener protectionListener = new PlayerProtectionListener();
        final WorldEditionListener worldEditionListener = new WorldEditionListener();

        getServer().getPluginManager().registerEvents(entityEditionListener, this);
        getServer().getPluginManager().registerEvents(protectionListener, this);
        getServer().getPluginManager().registerEvents(worldEditionListener, this);

    }

    @Override
    public void onDisable() {
        eventBus.onDisable();
        moduleManager.getModules().values().forEach(Module::onDisable);
    }

    public World getWorld() {
        return world;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
