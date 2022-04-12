package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import gq.engo.utils.TPS;
import net.minecraft.server.v1_12_R1.BlockRedstoneLamp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.Chunk;
import org.bukkit.event.block.BlockPhysicsEvent;

public class WaterLag implements Listener {
    int waterlimittps = Plugin.Instance.getConfig().getInt("Water-Lag.DisableTPS");
    boolean waterenabled = Plugin.Instance.getConfig().getBoolean("Water-Lag.Enabled");

    @EventHandler
    public void BlockEvent(BlockPhysicsEvent e) {
        if (!waterenabled) return;
        if (e.getBlock().getType().name().contains("WATER") || e.getBlock().getType().name().contains("LAVA")) {
            if (TPS.getTPS() < waterlimittps) {
                //ServerUtil.broadcast("TPS low, cancelling water update.");
                e.setCancelled(true);
                return;
            }
        }
    }
}