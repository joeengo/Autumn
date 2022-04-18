package gq.engo.listeners;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class DeathMessages implements Listener {

    public String getDeathMessageFromCause(DamageCause cause) {
        String[] messages = Plugin.Instance.getConfig().getStringList("DeathMessages.List." + cause.name()).toArray(new String[0]);
        if (messages == null || messages.length == 0) {
            messages = Plugin.Instance.getConfig().getStringList("DeathMessages.List.GENERAL").toArray(new String[0]);
        }

        Random rando = new Random();
        int random = rando.nextInt(messages.length);
        return messages[random];
    }

    public String getDeathMessageFromString(String cause) {
        String[] messages = Plugin.Instance.getConfig().getStringList("DeathMessages.List." + cause).toArray(new String[0]);
        if (messages == null || messages.length == 0) {
            messages = Plugin.Instance.getConfig().getStringList("DeathMessages.List.GENERAL").toArray(new String[0]);
        }

        Random rando = new Random();
        int random = rando.nextInt(messages.length);
        return messages[random];
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("DeathMessages.Enabled"))
        if (e.getEntity().getType() == EntityType.PLAYER) {
            String message = null;

            if (e.getEntity().getLastDamageCause() != null && getDeathMessageFromCause(e.getEntity().getLastDamageCause().getCause()) != null) {
                message = C.chat(getDeathMessageFromCause(e.getEntity().getLastDamageCause().getCause())).replaceAll("%player%", e.getEntity().getName());
            } else {
                message = C.chat(getDeathMessageFromString("GENERAL")).replaceAll("%player%", e.getEntity().getName());
            }
            if (e.getEntity().getKiller() != null) {
                message = message.replaceAll("%killer%", e.getEntity().getKiller().getName());
            } else {
                message = C.chat(getDeathMessageFromString("GENERAL")).replaceAll("%player%", e.getEntity().getName());
            }
            e.setDeathMessage(message);
        }
    }
}
