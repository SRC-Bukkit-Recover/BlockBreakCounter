package me.hsgamer.blockbreakcounter;

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
}
