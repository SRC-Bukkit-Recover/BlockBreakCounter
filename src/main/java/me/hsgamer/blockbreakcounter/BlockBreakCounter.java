package me.hsgamer.blockbreakcounter;

import me.hsgamer.blockbreakcounter.commands.Commands;
import me.hsgamer.blockbreakcounter.files.DataManager;
import me.hsgamer.blockbreakcounter.hooks.LeaderHeadsHook;
import me.hsgamer.blockbreakcounter.hooks.PAPIHook;
import me.hsgamer.blockbreakcounter.listeners.BlockBreakListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
        Metrics metrics = new Metrics(this);

        this.getConfig().options().copyHeader(true);
        saveDefaultConfig();

        File datadir = new File(getDataFolder().toPath().toString() + File.separator + "data");
        if (!datadir.exists()) {
            datadir.mkdirs();
        }
        List<String> blocks = getConfig().getStringList("blocks");
        for (String i : blocks) {
            try {
                Material.valueOf(i);
            } catch (IllegalArgumentException e) {
                getServer().getConsoleSender().sendMessage(ChatColor.RED + i + " is invalid. Ignored");
                continue;
            }
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

        this.getCommand("blockbreakcounter").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
    }

    @Override
    public void onDisable() {
        plugin = null;
        leaderHeadsHookHashMap.clear();
        DataManager.clearDataFiles();
        HandlerList.unregisterAll(this);
    }
}
