package gq.engo.commands;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UniqueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Boolean enabled = Plugin.Instance.getConfig().getBoolean("Commands.List.UniqueJoins");
        if(!enabled) return true;
        Player p = (Player) sender;
        p.sendMessage(C.chat(C.getPrefix() + C.getThird("") + "The unique amount of joins is: " + C.chat(C.getSecondary("" + (Bukkit.getOfflinePlayers().length)) + C.chat(C.getThird(" players.")))));
        return true;
    }
}
