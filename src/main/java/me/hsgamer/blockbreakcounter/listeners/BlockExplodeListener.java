package me.hsgamer.blockbreakcounter.listeners;

import me.hsgamer.blockbreakcounter.files.DataManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class BlockExplodeListener implements Listener {

    @EventHandler
    public void onBreakBlock(EntityExplodeEvent e) {
        if (e.isCancelled()) return;
        Entity entity = e.getEntity();
        if (!(entity instanceof TNTPrimed)) return;
        Entity source = ((TNTPrimed) entity).getSource();
        if (!(source instanceof Player)) return;
        String player = source.getName();
        List<Block> blocks = e.blockList();
        for (Block block : blocks) {
            if (DataManager.isAvailable(block.getType().toString())) {
                DataManager.add(block.getType().toString(), player, 1);
            }
        }
    }
}
