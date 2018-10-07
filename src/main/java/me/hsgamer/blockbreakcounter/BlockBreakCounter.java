package me.hsgamer.blockbreakcounter;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BlockBreakCounter extends JavaPlugin {
    private static BlockBreakCounter plugin;

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
        for (String i : getConfig().getStringList("blocks")) {
            DataManager.addDataFile(i);
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
