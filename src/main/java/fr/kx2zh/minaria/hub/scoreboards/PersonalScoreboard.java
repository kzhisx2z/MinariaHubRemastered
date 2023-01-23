package fr.kx2zh.minaria.hub.scoreboards;

import fr.kx2zh.minaria.api.MinariaAPI;
import fr.kx2zh.minaria.api.player.AbstractPlayerData;
import fr.kx2zh.minaria.hub.Hub;
import fr.kx2zh.minaria.hub.utils.NumberUtils;
import fr.kx2zh.minaria.tools.scoreboard.ObjectiveSign;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PersonalScoreboard {

    private final Hub hub;
    private final Player player;
    private final ObjectiveSign objectiveSign;

    private SimpleDateFormat dateFormat;
    private String rank;
    private long coins;
    private long stars;

    public PersonalScoreboard(Hub hub, Player player) {
        this.hub = hub;
        this.player = player;

        objectiveSign = new ObjectiveSign(MinariaAPI.get().getServerName().toLowerCase(), "Minaria");

        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void onLogout() {
        objectiveSign.removeReceiver(hub.getServer().getOfflinePlayer(player.getUniqueId()));
    }

    public void reloadData() {
        final AbstractPlayerData playerData = MinariaAPI.get().getPlayerDataManager().getPlayerData(player.getUniqueId());

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        rank = playerData.getPrefix();
        coins = playerData.getCoins();
        stars = playerData.getStars();
    }

    public void setLines(String ip) {
        objectiveSign.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + " Minaria ");
        objectiveSign.setLine(0, ChatColor.DARK_GRAY + dateFormat.format(new Date()) + " " + MinariaAPI.get().getServerName().toLowerCase());

        objectiveSign.setLine(1, ChatColor.BLUE + "");

        objectiveSign.setLine(2, ChatColor.GOLD + "» " + ChatColor.WHITE + player.getName());
        objectiveSign.setLine(3, ChatColor.GRAY + "   Grade: " + rank);
        objectiveSign.setLine(4, ChatColor.GRAY + "   Coins: " + ChatColor.AQUA + NumberUtils.format(coins));
        objectiveSign.setLine(5, ChatColor.GRAY + "   Stars: " + ChatColor.LIGHT_PURPLE + NumberUtils.format(stars));

        objectiveSign.setLine(6, ChatColor.GREEN + "");

        objectiveSign.setLine(7, ChatColor.YELLOW + ip);

        objectiveSign.updateLines();
    }
}

