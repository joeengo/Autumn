package gq.engo.commands;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VanishCommand implements CommandExecutor, Listener {
    public static final java.util.HashMap<Player, Boolean> vanished = new java.util.HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        String vanishenabled = Plugin.Instance.getConfig().getString("Commands.Vanished.Vanished-Enabled").replaceAll("%prefix%", C.getPrefix()).replaceAll("%thirdcolour%", C.getThird("")).replaceAll("%secondary-colour%", C.getSecondary(""));
        String vanishedisabled = Plugin.Instance.getConfig().getString("Commands.Vanished.Vanished-Disabled").replaceAll("%prefix%", C.getPrefix()).replaceAll("%thirdcolour%", C.getThird("")).replaceAll("%secondary-colour%", C.getSecondary(""));
        if (p.hasPermission("autumn.vanish")) {
            if (vanished.get(p) == null || vanished.get(p) == false) {
                vanished.put(p, true);
                for (Player i : Bukkit.getOnlinePlayers()) {
                    if (!i.hasPermission("autumn.vanish")) {
                        i.hidePlayer(Plugin.Instance, p);
                    }
                }
                p.sendMessage(C.chat(vanishenabled));
            } else {
                vanished.put(p, false);
                for (Player i : Bukkit.getOnlinePlayers()) {
                    if (!i.hasPermission("autumn.vanish")) {
                        i.showPlayer(Plugin.Instance, p);
                    }
                }
                p.sendMessage(C.chat(vanishedisabled));
            }
        } else {
            p.sendMessage(C.chat(C.getInsufPermission()));
        }

        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission("autumn.vanish")) {
            for (Player i : Bukkit.getOnlinePlayers()) {
                if (vanished.get(i) == true) {
                    e.getPlayer().hidePlayer(Plugin.Instance, i);
                }
            }
        }
    }

}
