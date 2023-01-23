package fr.kx2zh.minaria.hub.gui.main;

import fr.kx2zh.minaria.hub.Hub;
import fr.kx2zh.minaria.hub.gui.AbstractGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiMain extends AbstractGui {

    public GuiMain(Hub hub) {
        super(hub);
    }

    @Override
    public void display(Player player) {
        inventory = hub.getServer().createInventory(null, 45, ChatColor.GREEN + "" + ChatColor.BOLD + "Menu Principal");

        setSlotData(ChatColor.AQUA + "OP-PRISON", Material.STONE, 22, makeButtonLore(new String[] {"Revenez à l'ancien temps..."}, false, true), "prison");

        fillCorners(inventory, stainedGlassPane());

        player.openInventory(inventory);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action) {
        switch (action) {
            case "prison":
                break;

            default: break;
        }
    }

    private static ItemStack stainedGlassPane() {
        final ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private static String[] makeButtonLore(String[] description, boolean clickOpen, boolean clickTeleport) {
        final List<String> lore = new ArrayList<>();

        if (description != null) {
            for (String s : description) {
                lore.add(ChatColor.GRAY + s);
            }

            lore.add("");
        }

        if (clickOpen) {
            lore.add(ChatColor.GOLD + "\u25B6 Cliquez pour ouvrir le menu");
        }

        if (clickTeleport) {
            lore.add(ChatColor.GOLD + "\u25B6 Cliquez pour être téléporté");
        }

        return lore.toArray(new String[] {});
    }


}
