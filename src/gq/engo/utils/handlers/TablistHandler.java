package gq.engo.utils.handlers;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import gq.engo.utils.TPS;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

public class TablistHandler extends BukkitRunnable  {

    public static int Ping(Player p) {
        return ((CraftPlayer)p).getHandle().ping;
    }

    public String getTabWithFormat(Player p, String content) {
        String _f = C.chat(content);
        return _f.replaceAll("%ping%", ""+Ping(p)).replaceAll("%online%", ""+ServerUtil.getOnline().length).replaceAll("%tps%", TPS.getRoundedTPSWithColour());
    }

    @Override
    public void run() {

    }
}
