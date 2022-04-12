package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class SandLag implements Listener {
    @EventHandler
    public void EntityChangeBlockEvent(EntityChangeBlockEvent e) {
        if (e.getEntityType() == EntityType.FALLING_BLOCK) {
            boolean enabled = Plugin.Instance.getConfig().getBoolean("SandLag.Enabled");
            int limit = Plugin.Instance.getConfig().getInt("SandLag.EntityLimit");
            if (enabled == false) return;
            int fallingInChunk = ServerUtil.getEntitesInChunk(e.getBlock().getChunk(), EntityType.FALLING_BLOCK);
            if (fallingInChunk > limit) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
            }
        }
    }
}
