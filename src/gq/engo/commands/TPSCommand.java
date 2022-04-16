package gq.engo.commands;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.TPS;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPSCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String labels, String[] args) {
        Player p = (Player) sender;
        //if(p.hasPermission("autumn.tps")) {
            p.sendMessage(C.addPrefix("") + C.chat(C.getThird("") + "The servers current TPS is: " + C.chat(C.getSecondary("") + TPS.getRoundedTPSWithColour() + C.getThird(" [Predicted: ") + C.getSecondary(TPS.getRoundedTPS2WithColour()+"") + C.getThird("]"))));
        //}
        return true;
    }
}
