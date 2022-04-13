package gq.engo.listeners;

import gq.engo.Plugin;
import gq.engo.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Random;

public class RandomSpawn implements Listener {

    public static Location pickRandomLocation() {
        Location loc = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
        int x = Plugin.Instance.getConfig().getInt("RandomSpawn.RadiusMin") + new Random().nextInt(Plugin.Instance.getConfig().getInt("RandomSpawn.RadiusMax") - Plugin.Instance.getConfig().getInt("RandomSpawn.RadiusMin"));
        int z = Plugin.Instance.getConfig().getInt("RandomSpawn.RadiusMin") + new Random().nextInt(Plugin.Instance.getConfig().getInt("RandomSpawn.RadiusMax") - Plugin.Instance.getConfig().getInt("RandomSpawn.RadiusMin"));
        loc.setY(Bukkit.getServer().getWorlds().get(0).getHighestBlockAt(x, z).getY());
        loc.setX(x);
        loc.setZ(z);

        return loc;
    }

    @EventHandler
    public static void onRespawn(PlayerRespawnEvent e) {
        if (!Plugin.Instance.getConfig().getBoolean("RandomSpawn.Enabled")) return;
        Bukkit.getScheduler().runTaskLater(Plugin.Instance, new Runnable() {
            @Override
            public void run() {
                if (!e.isBedSpawn()) {
                    Location loc = pickRandomLocation();
                    e.setRespawnLocation(loc);
                    e.getPlayer().teleport(loc);
                }
            }
        }, 1);
    }


}
;