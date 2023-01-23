package fr.kx2zh.minaria.hub.module.modules.player;

import fr.kx2zh.minaria.hub.Hub;
import fr.kx2zh.minaria.hub.Permissions;
import fr.kx2zh.minaria.hub.cooldown.CooldownType;
import fr.kx2zh.minaria.hub.module.Module;
import fr.kx2zh.minaria.hub.module.ModuleType;
import fr.kx2zh.minaria.hub.utils.universal.XSound;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DoubleJump extends Module {

    private final List<UUID> allowedFlights = new ArrayList<>();

    public DoubleJump(Hub hub, ModuleType moduleType) {
        super(hub, moduleType);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.ADVENTURE) return;
        if (player.getAllowFlight()) return;
        if (!player.hasPermission(Permissions.DOUBLE_JUMP.getPermission())) return;
        if (((LivingEntity) player).isOnGround()) {
            player.setAllowFlight(true);
            allowedFlights.add(player.getUniqueId());
        }
    }


    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        final Player player = event.getPlayer();

        if (!allowedFlights.contains(player.getUniqueId())) return;
        allowedFlights.remove(player.getUniqueId());
        event.setCancelled(true);
        player.setAllowFlight(false);

        if (!tryCooldown(player.getUniqueId(), CooldownType.DOUBLE_JUMP, 3)) {
            player.sendMessage(ChatColor.RED + "Erreur » Il vous reste " + ChatColor.WHITE +
                    (getCooldown(player.getUniqueId(), CooldownType.DOUBLE_JUMP) / 1000) + ChatColor.RED + " seconde(s) à attendre.");
            return;
        }

        player.setVelocity(player.getLocation().getDirection().multiply(2D).setY(1.0D));

        for (int i = 0; i < 20; i++) {
            player.getWorld().playEffect(player.getLocation().subtract(0.0F, 0.20F, 0.0F), Effect.CLOUD, 2);
        }

        XSound.ENTITY_GENERIC_EXPLODE.playSound(player);
    }


}
