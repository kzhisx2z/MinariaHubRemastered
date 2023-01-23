package fr.kx2zh.minaria.hub.module.modules.gui;

import fr.kx2zh.minaria.hub.Hub;
import fr.kx2zh.minaria.hub.gui.AbstractGui;
import fr.kx2zh.minaria.hub.module.Module;
import fr.kx2zh.minaria.hub.module.ModuleType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GuiListener extends Module {

    public GuiListener(Hub hub, ModuleType moduleType) {
        super(hub, moduleType);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getCurrentItem() != null) {
            final Player player = (Player) event.getWhoClicked();
            final ItemStack item = event.getCurrentItem();
            final AbstractGui gui = (AbstractGui) hub.getGuiManager().getPlayerGui(player);

            if (event.getClickedInventory() instanceof PlayerInventory) {
                hub.getPlayerManager().getStaticInventory().doInteraction(player, item);
                return;
            }

            if (gui != null) {
                final String action = gui.getAction(event.getSlot());

                if (action != null) {
                    gui.onClick(player, item, action, event.getClick());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();

        if (hub.getGuiManager().getPlayerGui(player) != null) {
            hub.getGuiManager().removeClosedGui(player);
        }
    }
}
