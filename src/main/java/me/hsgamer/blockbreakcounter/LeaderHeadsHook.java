package me.hsgamer.blockbreakcounter;

import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LeaderHeadsHook extends OnlineDataCollector {
    private String material;

    LeaderHeadsHook(String material) {
        super(BlockBreakCounter.getPlugin().getDescription().getName().toLowerCase() + "-" + material.toLowerCase(), BlockBreakCounter.getPlugin().getDescription().getName(), BoardType.DEFAULT, "&a&l" + material.toLowerCase() + " broken", BlockBreakCounter.getPlugin().getDescription().getName().toLowerCase() + "-" + material.toLowerCase(), Arrays.asList(null, null, "&e{amount} blocks", null));
        this.material = material;
    }

    @Override
    public Double getScore(Player player) {
        return DataManager.getDataFile(material).getConfig().getDouble(player.getName(), 0);
    }
}
