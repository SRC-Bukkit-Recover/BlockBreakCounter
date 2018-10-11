package me.hsgamer.blockbreakcounter;

import me.hsgamer.blockbreakcounter.commands.Commands;
import me.hsgamer.blockbreakcounter.files.DataManager;
import me.hsgamer.blockbreakcounter.hooks.LeaderHeadsHook;
import me.hsgamer.blockbreakcounter.hooks.PAPIHook;
import me.hsgamer.blockbreakcounter.listeners.BlockBreakListener;
import me.hsgamer.blockbreakcounter.listeners.BlockExplodeListener;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
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
        new MetricsLite(this);

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

        if (getConfig().getBoolean("listeners.block-break")) {
            getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Registered BlockBreakListener");
        }
        if (getConfig().getBoolean("listeners.block-explode")) {
            getServer().getPluginManager().registerEvents(new BlockExplodeListener(), this);
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Registered BlockExplodeListener");
        }
    }

    @Override
    public void onDisable() {
        plugin = null;
        leaderHeadsHookHashMap.clear();
        DataManager.clearDataFiles();
        HandlerList.unregisterAll(this);
    }

    public static void reloadPlugin(CommandSender sender) {
        getPlugin().saveConfig();
        DataManager.clearDataFiles();
        HandlerList.unregisterAll(getPlugin());
        leaderHeadsHookHashMap.clear();
        List<String> blocks = getPlugin().getConfig().getStringList("blocks");
        for (String i : blocks) {
            try {
                Material.valueOf(i);
            } catch (IllegalArgumentException e) {
                sender.sendMessage(ChatColor.RED + i + " is invalid. Ignored");
                continue;
            }
            DataManager.addDataFile(i);
        }

        if (getPlugin().getServer().getPluginManager().isPluginEnabled("LeaderHeads")) {
            for (String i : blocks) {
                leaderHeadsHookHashMap.put(i, new LeaderHeadsHook(i));
            }
        }

        if (getPlugin().getConfig().getBoolean("listeners.block-break")) {
            getPlugin().getServer().getPluginManager().registerEvents(new BlockBreakListener(), getPlugin());
            sender.sendMessage(ChatColor.GREEN + "Registered BlockBreakListener");
        }
        if (getPlugin().getConfig().getBoolean("listeners.block-explode")) {
            getPlugin().getServer().getPluginManager().registerEvents(new BlockExplodeListener(), getPlugin());
            sender.sendMessage(ChatColor.GREEN + "Registered BlockExplodeListener");
        }
    }
}
