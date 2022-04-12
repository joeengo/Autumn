package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        if (!e.isBedSpawn()) {
            e.setRespawnLocation(pickRandomLocation());
            ServerUtil.broadcast("Spawning player @ " + e.getRespawnLocation());
        }
        ServerUtil.broadcast("Spawning player @ " + e.getRespawnLocation());
    }


}
;