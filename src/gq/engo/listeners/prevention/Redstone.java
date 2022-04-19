package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import gq.engo.utils.TPS;
import org.bukkit.block.Block;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class Redstone implements Listener {
    public final java.util.HashMap<Player, Integer> flicks = new java.util.HashMap<>();
    public final java.util.HashMap<Block, Integer> lamps = new java.util.HashMap<>();
    String playername = "";
    String cachedname = "";



    @EventHandler
    public void piston(BlockPistonRetractEvent e) {
        boolean enabled = Plugin.Instance.getConfig().getBoolean("Redstone.Enabled");
        int limit = Plugin.Instance.getConfig().getInt("Redstone.DisableTPS");
        double tps = TPS.getRoundedTPS2();
        if (enabled == false) return;
        if (tps < limit) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void piston2(BlockPistonExtendEvent e) {
        boolean enabled = Plugin.Instance.getConfig().getBoolean("Redstone.Enabled");
        int limit = Plugin.Instance.getConfig().getInt("Redstone.DisableTPS");
        double tps = TPS.getRoundedTPS2();
        if (enabled == false) return;
        if (tps < limit) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void redstoneUpdate(BlockRedstoneEvent e) {
        boolean enabled = Plugin.Instance.getConfig().getBoolean("Redstone.Enabled");
        int limit = Plugin.Instance.getConfig().getInt("Redstone.DisableTPS");
        double tps = TPS.getRoundedTPS2();
        if (enabled == false) return;
        if (tps < limit) {
            e.getBlock().breakNaturally();
            e.setNewCurrent(e.getOldCurrent());

            Entity[] entities = e.getBlock().getChunk().getEntities();
            for (Entity ent : entities) {
                if (ent.getType() == EntityType.PLAYER) {
                    playername = ent.getName();
                    //ent.sendMessage(C.chat(C.getPrefix() + C.getSecondary("Redstone device") + C.getThird("") + " destroyed due to suspected " + C.getSecondary("lag") + C.getThird(".")));
                }
            }
            if (playername != cachedname) {
                cachedname = playername;
                ServerUtil.broadcastToOps(C.addPrefix(C.getSecondary(playername) + C.getThird(" has been in a chunk with redstone while the server was lagging, it has been frozen!")));
            }
        }

        if (e.getBlock().getType().toString().toLowerCase().contains("redstone_lamp") && e.getBlock().getLocation().getY() >= Plugin.Instance.getConfig().getInt("Redstone.LampY")) {
            if (lamps.containsKey(e.getBlock())) {
                lamps.put(e.getBlock(), lamps.get(e.getBlock()) + 1);
            } else {
                lamps.put(e.getBlock(), 1);
            }
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        lamps.put(e.getBlock(), lamps.get(e.getBlock())-1);
                    }
                },
                3000
            );

            if (lamps.get(e.getBlock()) > Plugin.Instance.getConfig().getInt("Redstone.LampLimit")) {
                e.setNewCurrent(e.getOldCurrent());
            }
        }
    }

    // anti lever spam
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && (e.getClickedBlock().getType() == Material.LEVER || e.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR)) {
            int limit = Plugin.Instance.getConfig().getInt("Redstone.MaxLeverCPS");
            if (flicks.containsKey(e.getPlayer())) {
                flicks.put(e.getPlayer(), flicks.get(e.getPlayer()) + 1);
            } else {
                flicks.put(e.getPlayer(), 1);
            }
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        flicks.put(e.getPlayer(), flicks.get(e.getPlayer())-1);
                    }
                },
                1000
            );

            if (flicks.get(e.getPlayer()) > limit) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(C.addPrefix(C.getThird("Please stop interacting so fast!")));
            }
        }
    }
}
