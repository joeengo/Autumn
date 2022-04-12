package gq.engo.commands;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Boolean enabled = Plugin.Instance.getConfig().getBoolean("Commands.List.Say");
        if(!enabled) return true;
        Player p = (Player) sender;
        String formattedStr = String.join(" ", args);
        if (s.equalsIgnoreCase("say") && p.isOp()) {
                ServerUtil.broadcast(C.addPrefix(C.getSecondary(C.chat(formattedStr))));
        }
        return true;
    }
}

