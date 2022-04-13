package gq.engo.commands;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutumnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        p.sendMessage(C.chat("&7&m------------------"));
        p.sendMessage(C.chat("&6&lAutumn"));
        p.sendMessage(C.chat(""));
        p.sendMessage(C.chat("&eThis server is currently using ") + C.chat(C.getSecondary("Autumn v" + Plugin.Instance.getDescription().getVersion())));
        p.sendMessage(C.chat("&eMade by" + C.getSecondary(" engo & PceLmao")));
        p.sendMessage(C.chat(""));
        p.sendMessage(C.chat("&7&ohttps://github.com/joeengo/Autumn"));
        p.sendMessage(C.chat("&7&m------------------"));


        return true;
    }
}
