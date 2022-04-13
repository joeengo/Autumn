package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;

public class AntiOp implements Listener {

    public boolean canBeOperator(Player p) {
        String[] ss = Plugin.Instance.getConfig().getStringList("AntiOp.Operators").toArray(new String[0]);
        for (String s : ss) {
            if (p.getName().toString().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!Plugin.Instance.getConfig().getBoolean("AntiOp.Enabled")) return;
        if (!canBeOperator(e.getPlayer())) {
            e.getPlayer().setOp(false);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (!Plugin.Instance.getConfig().getBoolean("AntiOp.Enabled")) return;
        if (!canBeOperator(e.getPlayer())) {
            e.getPlayer().setOp(false);
        }
    }

}
