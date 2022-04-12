package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class BowBomb implements Listener {

    @EventHandler
    public void onProjectile(ProjectileLaunchEvent e) {

        //Credit AEF for guidance on this patch...

        Boolean enabled = Plugin.Instance.getConfig().getBoolean("BowBomb.Enabled");
        Integer max = Plugin.Instance.getConfig().getInt("BowBomb.MaxVelocity");
        if (!enabled) return;
        if (e.getEntity().getType() == EntityType.ARROW || e.getEntity().getType() == EntityType.SPECTRAL_ARROW || e.getEntity().getType() == EntityType.TIPPED_ARROW) {
            if(e.getEntity().getVelocity().lengthSquared() > max) {
                e.setCancelled(true);
            }
        }
    }
}
