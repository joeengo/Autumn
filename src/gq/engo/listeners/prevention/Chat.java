package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {
    public final java.util.HashMap<Player, Integer> chats = new java.util.HashMap<>();

    /*
    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        int limit = Plugin.Instance.getConfig().getInt("Chat.MaxPerSecond");
        boolean enabled = Plugin.Instance.getConfig().getBoolean("Chat.Enabled");
        if(enabled == false) { return; }
        if (chats.containsKey(e.getPlayer())) {
            chats.put(e.getPlayer(), chats.get(e.getPlayer()) + 1);
        } else {
            chats.put(e.getPlayer(), 1);
        }
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        chats.put(e.getPlayer(), chats.get(e.getPlayer())-1);
                    }
                },
                1000
        );
        if (chats.get(e.getPlayer()) > limit) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(C.addPrefix(C.getThird("You are chatting too fast! Chat Cooldown: " + (1/limit) + "s")));
            if (chats.get(e.getPlayer()) > limit+3 && Plugin.Instance.getConfig().getBoolean("Chat.Kick")) {
                Bukkit.getScheduler().runTask(Plugin.Instance, new Runnable() {
                    public void run() {
                        e.getPlayer().kickPlayer(C.addPrefix(C.getThird("You are chatting too fast! Chat Cooldown: " + (1/limit) + "s")));
                    }
                });
            }
        }
    }
    */
}
