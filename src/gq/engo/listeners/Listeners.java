package gq.engo.listeners;

import gq.engo.utils.C;
import gq.engo.Plugin;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import gq.engo.utils.ServerUtil;

public class Listeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        boolean newsmessagesenabled = Plugin.Instance.getConfig().getBoolean("Joins.NewsMessages.Enabled");
        boolean joinmessagesenabled = Plugin.Instance.getConfig().getBoolean("Joins.JoinMessages.Enabled");
        if (joinmessagesenabled == true) {
            String joinmsg = Plugin.Instance.getConfig().getString("Joins.JoinMessages.Message").replaceAll("%player%", p.getName()).replaceAll("%prefix%", C.getPrefix());
            if (!ServerUtil.isVanished(p)) {
                ServerUtil.broadcast(C.chat(joinmsg));
            }
            e.setJoinMessage(null);
        }
        if (newsmessagesenabled == true) {
            String newsmsg = Plugin.Instance.getConfig().getString("Joins.NewsMessages.Message").replaceAll("%player%", p.getName()).replaceAll("%prefix%", C.getPrefix());
            p.sendMessage(C.chat(newsmsg));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        boolean quitmessagesenabled = Plugin.Instance.getConfig().getBoolean("Quits.QuitMessages.Enabled");
        boolean deop = Plugin.Instance.getConfig().getBoolean("Quits.Deop");
        boolean gm = Plugin.Instance.getConfig().getBoolean("Quits.Gamemode");
        if (quitmessagesenabled == true) {
            String quitmsg = Plugin.Instance.getConfig().getString("Quits.QuitMessages.Message").replaceAll("%player%", p.getName());
            if (!ServerUtil.isVanished(p)) {
                ServerUtil.broadcast(C.chat(quitmsg));
            }
            e.setQuitMessage(null);
        }

        if (deop && p.isOp()) {
                p.setOp(false);
        }

        if (gm && (p.getGameMode() == GameMode.CREATIVE)) {
            p.setGameMode(GameMode.SURVIVAL);
        }
    }
}
