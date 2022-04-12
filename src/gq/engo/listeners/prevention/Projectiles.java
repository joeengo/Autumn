package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.ServerUtil;
import gq.engo.utils.TPS;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class Projectiles implements Listener {
    @EventHandler
    public void Proj(ProjectileLaunchEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("Projectiles.Enabled")) {
            if (TPS.getTPS() < Plugin.Instance.getConfig().getInt("Projectiles.DisableTPS")) {
                e.setCancelled(true);
                return;
            }
            int amt = 0;
            int area = Plugin.Instance.getConfig().getInt("Projectiles.Area");
            for (Entity i : e.getEntity().getNearbyEntities(area, area, area)) {
                if (i.getType() == e.getEntity().getType()) {
                    amt += 1;
                }
            }
            if (amt > Plugin.Instance.getConfig().getInt("Projectiles.MaxPerArea")) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {

    }
}
