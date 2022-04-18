package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class Chat implements Listener {
    List<String> blacklist = Plugin.Instance.getConfig().getStringList("Chat.Blacklist");
    List<String> whitelist = Plugin.Instance.getConfig().getStringList("Chat.Whitelist");
    boolean enabled = Plugin.Instance.getConfig().getBoolean("Chat.Enabled");

    public boolean isBlacklisted(String s) {
        for (String i : blacklist) {
            if (s.toLowerCase().contains(i)) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!enabled) return;
        String checkmesssage = e.getMessage();
        for (String i : whitelist) {
            checkmesssage = checkmesssage.replaceAll(i, "");
        }

        if (isBlacklisted(checkmesssage)) {
            e.getPlayer().sendMessage("<" + e.getPlayer().getName() + "> " + (e.getMessage().replaceAll(">", "")));
            e.setCancelled(true);
        }
    }
}
