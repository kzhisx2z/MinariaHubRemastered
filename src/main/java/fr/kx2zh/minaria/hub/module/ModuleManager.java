package fr.kx2zh.minaria.hub.module;

import fr.kx2zh.minaria.hub.Hub;
import fr.kx2zh.minaria.hub.module.modules.gui.GuiListener;
import fr.kx2zh.minaria.hub.module.modules.player.DoubleJump;
import fr.kx2zh.minaria.hub.module.modules.player.PlayerListener;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    private Hub hub;
    private final Map<ModuleType, Module> modules = new HashMap<>();

    public void loadModules(Hub hub) {
        this.hub = hub;

        if (!modules.isEmpty()) unloadModules();

        registerModule(new DoubleJump(hub, ModuleType.DOUBLE_JUMP));
        registerModule(new PlayerListener(hub, ModuleType.PLAYER_LISTENER));
        registerModule(new GuiListener(hub, ModuleType.GUI_LISTENER));
    }

    private void unloadModules() {
        for (Module module : modules.values()) {
            HandlerList.unregisterAll(module);
            module.onDisable();
        }
    }

    private void registerModule(Module module) {
        hub.getServer().getPluginManager().registerEvents(module, hub);
        modules.put(module.getModuleType(), module);
    }

    public Map<ModuleType, Module> getModules() {
        return modules;
    }
}
