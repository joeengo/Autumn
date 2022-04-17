package gq.engo.runnables;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import gq.engo.utils.TPS;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import com.sun.management.OperatingSystemMXBean;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class TablistRunnable implements Runnable {

    @Override
    public void run() {
        PacketPlayOutPlayerListHeaderFooter out = new PacketPlayOutPlayerListHeaderFooter();
        for (Player p : Bukkit.getOnlinePlayers()) {
            String header = String.join("\n&r", Plugin.Instance.getConfig().getStringList("Tablist.Header"));
            String footer = String.join("\n&r", Plugin.Instance.getConfig().getStringList("Tablist.Footer"));

            // TODO: Make this into a method, not ugly lines of code

            header = header.replaceAll("%real-tps%", TPS.getRoundedTPS2WithColour()).replaceAll("%tps%", TPS.getRoundedTPSWithColour()).replaceAll("%ping%", String.valueOf(((CraftPlayer) p).getHandle().ping)).replaceAll("%online%", String.valueOf(ServerUtil.getOnline())).replaceAll("%uptime%", ServerUtil.formatTime(Plugin.Time - System.currentTimeMillis()));
            footer = footer.replaceAll("%real-tps%", TPS.getRoundedTPS2WithColour()).replaceAll("%tps%", TPS.getRoundedTPSWithColour()).replaceAll("%ping%", String.valueOf(((CraftPlayer) p).getHandle().ping)).replaceAll("%online%", String.valueOf(ServerUtil.getOnline())).replaceAll("%uptime%", ServerUtil.formatTime(Plugin.Time - System.currentTimeMillis()));

            header = header.replaceAll("%usedmem%", ServerUtil.RAM(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())).replaceAll("%totalmem%", ServerUtil.RAM(Runtime.getRuntime().maxMemory()));
            footer = footer.replaceAll("%usedmem%", ServerUtil.RAM(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())).replaceAll("%totalmem%", ServerUtil.RAM(Runtime.getRuntime().maxMemory()));

            header = C.chat(header);
            footer = C.chat(footer);
            ChatComponentText headerComp = new ChatComponentText(header);
            ChatComponentText footerComp = new ChatComponentText(footer);
            try {
                Field a = out.getClass().getDeclaredField("a");
                Field b = out.getClass().getDeclaredField("b");
                a.setAccessible(true);
                b.setAccessible(true);
                a.set(out, headerComp);
                b.set(out, footerComp);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(out);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
