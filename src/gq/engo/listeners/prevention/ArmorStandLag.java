package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.ServerUtil;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ArmorStandLag implements Listener {


    @EventHandler
    public void onArmorStandPlace(EntitySpawnEvent e) {
        if (e.getEntity().getType() == EntityType.ARMOR_STAND) {
            int limit = Plugin.Instance.getConfig().getInt("ArmorStands.PerChunk");
            if (ServerUtil.getEntitesInChunk(e.getEntity().getLocation().getChunk(), EntityType.ARMOR_STAND) > limit) {
                e.setCancelled(true);
            }
        }
    }
}
