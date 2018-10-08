package me.hsgamer.blockbreakcounter.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.hsgamer.blockbreakcounter.BlockBreakCounter;
import me.hsgamer.blockbreakcounter.files.DataManager;
import org.bukkit.entity.Player;

import java.util.List;

public class PAPIHook extends PlaceholderExpansion {
    private BlockBreakCounter plugin = BlockBreakCounter.getPlugin();

    public String getIdentifier() {
        return plugin.getDescription().getName();
    }

    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public String onPlaceholderRequest(Player p, String params) {
        if (p == null) {
            return "";
        }

        // %PLUGINNAME_count_MATERIAL%
        if (params.startsWith("count_")) {
            List<String> types = DataManager.getKeys();
            String result = "";
            for (String i : types) {
                if (params.equalsIgnoreCase("count_" + i)) {
                    result = String.valueOf(DataManager.getDataFile(i).getConfig().getInt(p.getName()));
                }
            }
            return result;
        }

        return null;

    }
}
