package me.hsgamer.blockbreakcounter;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataFiles {
    private FileConfiguration config;
    private File configFile;
    private BlockBreakCounter plugin = BlockBreakCounter.getPlugin();
    private String filename;

    DataFiles(String filename) {
        this.filename = filename;
        setUpConfig();
    }

    private void setUpConfig() {
        configFile = new File(plugin.getDataFolder().toPath().toString() + File.separator + "data", filename + ".yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                config = YamlConfiguration.loadConfiguration(configFile);
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Created " + filename + ".yml");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create the " + filename + ".yml file");
            }
        } else {
            config = YamlConfiguration.loadConfiguration(configFile);
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded " + filename + ".yml");
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            setUpConfig();
        }
        return config;
    }
}
