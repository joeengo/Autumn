package gq.engo.utils;

import gq.engo.Plugin;
import org.bukkit.ChatColor;

public class C {
    public static String chat (String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getSecondary(String s) {
        return Plugin.Instance.getConfig().getString("Secondary-Colour") + chat(s);
    }

    public static String addPrefix (String s) {
        return chat(Plugin.Instance.getConfig().getString("Prefix") + getThird(s));
    }

    public static String getPrefix () {
        return chat(getThird("") + Plugin.Instance.getConfig().getString("Prefix"));
    }


    public static String getThird(String s) {
        return Plugin.Instance.getConfig().getString("Third-Colour") + chat(s);
    }

    public static String getInsufPermission() {
        return Plugin.Instance.getConfig().getString("Commands.Insufficient-Permission.Message").replaceAll("%prefix%", C.getPrefix()).replaceAll("%thirdcolour%", C.getThird("")).replaceAll("%secondary-colour%", C.getSecondary(""));
    }

}