package gq.engo.commands;

import gq.engo.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import gq.engo.utils.C;

public class HelpCommand implements CommandExecutor {

    /**
     * TODO:
     *
     * Fix the \n bug
     *
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String labels, String[] args) {
        String help = String.join("\n", Plugin.Instance.getConfig().getStringList("Commands.Help.Message"));
        Player p = (Player) sender;
        p.sendMessage(C.chat(help));
        return true;
    }
}
