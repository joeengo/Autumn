package gq.engo.listeners.prevention;


import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.TPS;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class Withers implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        boolean enabled = Plugin.Instance.getConfig().getBoolean("WitherSpawn.Enabled");
        int limit = Plugin.Instance.getConfig().getInt("WitherSpawn.MaxPerChunk");
        if (!enabled) return;

        Integer tps = Plugin.Instance.getConfig().getInt("WitherSpawn.TPS");
        if(e.getEntityType() == EntityType.WITHER && TPS.getRoundedTPS() < tps) {
            e.setCancelled(true);
            for ( Entity i : e.getEntity().getNearbyEntities(20, 20, 20)) {
                if (i.getType() == EntityType.PLAYER) {
                    i.sendMessage(C.chat(C.addPrefix(C.getThird("Spawning withers is disabled under ")) + C.getSecondary(tps+" ") + C.getThird("TPS")));
                }
            }
        }

        int i = 0;
        for (Entity entity : e.getLocation().getChunk().getEntities()) {
            if (entity.getType() == EntityType.WITHER) {
                i = i + 1;
            }
        }
        if (i > limit) {
            e.setCancelled(true);
        }

    }

}
