package me.hsgamer.blockbreakcounter.files;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {
    private static HashMap<String, DataFiles> dataFiles = new HashMap<>();

    public static void addDataFile(String filename) {
        dataFiles.put(filename, new DataFiles(filename));
    }

    public static HashMap<String, DataFiles> getDataFiles() {
        return dataFiles;
    }

    public static void clearDataFiles() {
        dataFiles.clear();
    }

    public static boolean isAvailable(String filename) {
        return dataFiles.containsKey(filename);
    }

    public static DataFiles getDataFile(String filename) {
        return dataFiles.get(filename);
    }

    public static List<String> getKeys() {
        return new ArrayList<>(dataFiles.keySet());
    }

    public static void add(String filename, String playername, int number) {
        DataFiles datafile = getDataFile(filename);
        FileConfiguration data = datafile.getConfig();
        if (!data.contains(playername)) {
            data.set(playername, number);
        } else {
            data.set(playername, data.getInt(playername) + number);
        }
        datafile.saveConfig();
    }

    public static void set(String filename, String playername, int number) {
        DataFiles datafile = getDataFile(filename);
        FileConfiguration data = datafile.getConfig();
        data.set(playername, number);
        datafile.saveConfig();
    }

    public static void clearData(String filename, String playername) {
        DataFiles datafile = getDataFile(filename);
        FileConfiguration data = datafile.getConfig();
        if (data.contains(playername)) {
            data.set(playername, 0);
        }
        datafile.saveConfig();
    }

    public static void clearAll(String filename) {
        DataFiles datafile = getDataFile(filename);
        FileConfiguration data = datafile.getConfig();
        List<String> players = data.getStringList("");
        if (players == null) return;
        for (String player : players) {
            data.set(player, 0);
        }
        datafile.saveConfig();
    }
}
