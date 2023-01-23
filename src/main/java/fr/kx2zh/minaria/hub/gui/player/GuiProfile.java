package fr.kx2zh.minaria.hub.gui.player;

import fr.kx2zh.minaria.api.MinariaAPI;
import fr.kx2zh.minaria.api.player.AbstractPlayerData;
import fr.kx2zh.minaria.hub.Hub;
import fr.kx2zh.minaria.hub.gui.AbstractGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GuiProfile extends AbstractGui {
    public GuiProfile(Hub hub) {
        super(hub);
    }

    @Override
    public void display(Player player) {
        inventory = hub.getServer().createInventory(null, 45, ChatColor.AQUA + "" + ChatColor.BOLD + "Profile");

        final AbstractPlayerData playerData = MinariaAPI.get().getPlayerDataManager().getPlayerData(player.getUniqueId());
        final String firstConnection = new SimpleDateFormat("dd/MM/yyyy à HH:mm:ss").format(new Date(playerData.getFirstLogin().getTime()));

        setSlotData(inventory, ChatColor.YELLOW + "" + ChatColor.BOLD + player.getName(), skullItem(player), 22, new String[] {
                ChatColor.DARK_GRAY + "Statistique de jeux",
                " ",
                ChatColor.GRAY + "- " + ChatColor.WHITE + "Grade: " + playerData.getPrefix(),
                ChatColor.GRAY + "- " + ChatColor.WHITE + "Première connexion: " + ChatColor.AQUA + firstConnection
        }, "prison");

        fillCorners(inventory, stainedGlassPane());

        player.openInventory(inventory);
    }

    private static ItemStack stainedGlassPane() {
        final ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack skullItem(Player player) {
        final ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(player.getName());
        item.setItemMeta(meta);

        return item;
    }
}
