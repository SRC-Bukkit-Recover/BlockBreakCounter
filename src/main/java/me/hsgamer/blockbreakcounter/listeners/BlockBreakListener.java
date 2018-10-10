package me.hsgamer.blockbreakcounter.listeners;

import me.hsgamer.blockbreakcounter.files.DataManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakBlock(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        Material block = e.getBlock().getType();
        if (DataManager.isAvailable(block.toString())) {
            DataManager.add(block.toString(), e.getPlayer().getName(), 1);
        }
    }
}
