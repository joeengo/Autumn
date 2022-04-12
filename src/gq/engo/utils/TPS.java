package gq.engo.utils;

import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.Bukkit;

public class TPS implements Runnable
{
    public static int TICK_COUNT= 0;
    public static long[] TICKS= new long[600];
    public static long LAST_TICK= 0L;

    @SuppressWarnings("deprecation")
    public static double getTPS() {
        return (MinecraftServer.getServer().recentTps[0]);
    }

    public static double getTPS2()
    {
        return getTPS3(100);
    }

    public static double getTPS3(int ticks)
    {
        double returnMe = 0;
        try {
            if (TICK_COUNT < ticks) {
                return 20.0D;
            }
            int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
            long elapsed = System.currentTimeMillis() - TICKS[target];
            returnMe = ticks / (elapsed / 1000.0D);

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(e.getStackTrace()));
            returnMe = 0;
        }
        return returnMe;
    }

    public static double getRoundedTPS() {
        double tps = getTPS();
        return Math.max(0, Math.min(20, (Math.round(getTPS() * 10000) / 10000)));
    }


    public static String getRoundedTPSWithColour() {
        double tps = getRoundedTPS();
        String colour = "";
        if (tps > 14) {
            colour = "&a";
        } else {
            if (tps > 10) {
                colour = "&e";
            } else {
                colour = "&c";
            }
        }
        return colour+tps;
    }

    public static double getRoundedTPS2() {
        double tps = getTPS2();
        return Math.max(0, Math.min(20, (Math.round(tps * 100) / 100)));
    }


    public static String getRoundedTPS2WithColour() {
        double tps = getRoundedTPS2();
        String colour = "";
        if (tps > 14) {
            colour = "&a";
        } else {
            if (tps > 10) {
                colour = "&e";
            } else {
                colour = "&c";
            }
        }
        return colour+tps;
    }

    public static long getElapsed(int tickID)
    {
        if (TICK_COUNT- tickID >= TICKS.length)
        {
        }

        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    public void run()
    {
        TICKS[(TICK_COUNT% TICKS.length)] = System.currentTimeMillis();

        TICK_COUNT+= 1;
    }
}