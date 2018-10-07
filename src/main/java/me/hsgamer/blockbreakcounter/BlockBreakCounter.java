package me.hsgamer.blockbreakcounter;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public final class BlockBreakCounter extends JavaPlugin {
    private static BlockBreakCounter plugin;
    private static HashMap<String, LeaderHeadsHook> leaderHeadsHookHashMap = new HashMap<>();

    public static BlockBreakCounter getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.getConfig().options().copyHeader(true);
        saveDefaultConfig();

        File datadir = new File(getDataFolder().toPath().toString() + File.separator + "data");
        if (!datadir.exists()) {
            datadir.mkdirs();
        }
        List<String> blocks = getConfig().getStringList("blocks");
        for (String i : blocks) {
            DataManager.addDataFile(i);
        }

        if (getServer().getPluginManager().isPluginEnabled("LeaderHeads")) {
            for (String i : blocks) {
                leaderHeadsHookHashMap.put(i, new LeaderHeadsHook(i));
            }
        }

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PAPIHook().register();
        }


        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        plugin = null;
        DataManager.clearDataFiles();
        HandlerList.unregisterAll(this);
    }
}
