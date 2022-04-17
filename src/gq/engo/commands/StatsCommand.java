package gq.engo.commands;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Objects;

public class StatsCommand implements CommandExecutor {

    private static long getFolderSize(File folder)
    {
        long length = 0;
        File[] files = folder.listFiles();
        int len = files.length;
        for (int i = 0; i < len; i++) {
            if (files[i].isFile()) {
                length += files[i].length();
            }
            else {
                length += getFolderSize(files[i]);
            }
        }
        return length;
    }
    private static long calcWorld() {
        long len = 0;
        for (World i : Bukkit.getWorlds()) {
            len = len + getFolderSize(Objects.requireNonNull(new File(i.getName())));
        }
        return len;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        String message =  String.join("\n", Plugin.Instance.getConfig().getStringList("Commands.Stats.Message")).replaceAll("%size%", (calcWorld()/(1024*1024*1024))+" GB");
        p.sendMessage(C.chat(message));
        return true;
    }
}
