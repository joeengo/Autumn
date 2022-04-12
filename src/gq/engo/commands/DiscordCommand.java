package gq.engo.commands;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Boolean enabled = Plugin.Instance.getConfig().getBoolean("Commands.List.Discord");
        if(!enabled) return true;
        Player p = (Player) sender;
        p.sendMessage(C.chat(C.addPrefix("") + C.getThird("") + "Discord: " + C.getSecondary("") + Plugin.Instance.getConfig().getString("Discord")));

        return true;
    }
}
