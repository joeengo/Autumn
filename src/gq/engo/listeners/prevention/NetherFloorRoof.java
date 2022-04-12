package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NetherFloorRoof implements Listener {

    int floor = Plugin.Instance.getConfig().getInt("NetherRoof&Floor.FloorY");
    int roof = Plugin.Instance.getConfig().getInt("NetherRoof&Floor.RoofY");

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(p.getWorld().getEnvironment() == World.Environment.NETHER) {
            if(p.getLocation().getY() > roof) {
                Location location = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getBlockY()-1, p.getLocation().getZ());
                p.teleport(location);
                p.sendMessage(C.chat(C.getPrefix() + C.getThird("You aren't allowed to go over the nether roof.")));
            }
        }
        if (p.getWorld().getEnvironment() == World.Environment.NETHER || p.getWorld().getEnvironment() == World.Environment.NORMAL) {
            if(p.getLocation().getY() < floor) {
                Location location = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getBlockY()+1, p.getLocation().getZ());
                p.teleport(location);
                p.sendMessage(C.chat(C.getPrefix() + C.getThird("You aren't allowed to go below the floor.")));
            }
        }
    }

}
