package gq.engo.commands;

import gq.engo.Plugin;
import gq.engo.utils.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DupeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Boolean enabled = Plugin.Instance.getConfig().getBoolean("Commands.List.Dupe");
        if(!enabled) return true;
        Player p = (Player) sender;
        ItemStack i = p.getInventory().getItemInMainHand();
        if (i == null) {
            p.sendMessage(C.addPrefix("Must be an item im your hand!"));
            return true;
        }

        if (p.isOp()) {
            i.setAmount(127);
        } else {
            p.sendMessage(C.chat(C.getInsufPermission()));
        }
        return true;
    }
}