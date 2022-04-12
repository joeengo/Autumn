package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.event.onTPSUpdate;
import gq.engo.utils.TPS;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobAI implements Listener {

    public static void onEnable() {
        for (World world : Plugin.Instance.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (livingEntity.hasAI()) {
                        livingEntity.setAI(false);
                    }
                }
            }
        }
    }

    public static void onDisable() {
        for (World world : Plugin.Instance.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (!livingEntity.hasAI()) {
                        livingEntity.setAI(true);
                    }
                }
            }
        }
    }

    public static void on(EntitySpawnEvent e) {
        LivingEntity live = (LivingEntity) e.getEntity();
        if (TPS.getTPS() < Plugin.Instance.getConfig().getInt("Entity.AIDisableTPS")) {
            live.setAI(false);
        } else {
            live.setAI(true);
        }
    }

    public static void ontps(onTPSUpdate e) {
        if (e.getNewTPS() < Plugin.Instance.getConfig().getInt("Entity.AIDisableTPS")) {
            onEnable();
        } else {
            onDisable();
        }
    }

}
