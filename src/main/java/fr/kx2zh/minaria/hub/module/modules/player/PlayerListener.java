package fr.kx2zh.minaria.hub.module.modules.player;

import fr.kx2zh.minaria.hub.Hub;
import fr.kx2zh.minaria.hub.module.Module;
import fr.kx2zh.minaria.hub.module.ModuleType;
import fr.kx2zh.minaria.tools.Titles;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener extends Module {

    public PlayerListener(Hub hub, ModuleType moduleType) {
        super(hub, moduleType);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            if (event.getEntity() instanceof Player) {
                Titles.sendTitle((Player) event.getEntity(), 20, 75, 20, ChatColor.YELLOW + "Ou voullez-vous aller ?", null);
            }

            event.getEntity().teleport(hub.getPlayerManager().getSpawn());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        hub.getEventBus().onLogin(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        hub.getEventBus().onLogout(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = event.getItem();

        if(itemStack == null) return;

        hub.getServer().getScheduler().runTaskAsynchronously(hub, () -> hub.getPlayerManager().getStaticInventory().doInteraction(player, itemStack));
    }

}
