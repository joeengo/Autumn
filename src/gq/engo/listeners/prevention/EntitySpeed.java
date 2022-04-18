package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public class EntitySpeed implements Listener {

    public final java.util.HashMap<Player, Boolean> exclude = new java.util.HashMap<>();

    public double cvrt(double num) {
        if (num < 0) {
            num = -num;
        }
        return num;
    }

    public boolean isExcluded(Player p) {
        if (exclude.get(p) == null || exclude.get(p) == false) {
            return false;
        } else return true;
    }

    public void setExcluded(Player p, boolean b) {
        exclude.put(p, b);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer().isInsideVehicle() && !isExcluded(e.getPlayer())) {
            double speed = cvrt(e.getFrom().lengthSquared() - e.getTo().lengthSquared());
            if (speed > Plugin.Instance.getConfig().getInt("EntitySpeed.MaxSpeed") * 1000) {
                e.setCancelled(true);
                e.getPlayer().teleport(e.getFrom());
                e.getPlayer().sendMessage(C.addPrefix("You are going too fast!"));
            }
        }
    }

    @EventHandler
    public void onMount(EntityMountEvent e) {
        if (e.getEntity().getType().equals(EntityType.PLAYER)) {
            setExcluded((Player) e.getEntity(), true);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            setExcluded((Player) e.getEntity(), false);
                        }
                    },
                    1000
            );
        }
    }

}
