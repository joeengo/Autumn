package gq.engo.commands;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Boolean enabled = Plugin.Instance.getConfig().getBoolean("Commands.List.Kill");
        if(!enabled) return true;
        Player p = (Player) sender;
        //if (s.equalsIgnoreCase("kill")) {
            p.sendMessage(C.chat(C.addPrefix("") + C.getThird("") + "&7You have been " + C.getSecondary("") + "Killed" + C.getThird("") + "."));
            p.setHealth(0);
        //}

        return true;
    }
}
