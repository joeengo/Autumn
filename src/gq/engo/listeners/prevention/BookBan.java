package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookBan implements Listener {
    @EventHandler
    public void PlayerEditBookEvent(PlayerEditBookEvent e) {
        boolean enabled = Plugin.Instance.getConfig().getBoolean("Books.Enabled");
        int charlimit = Plugin.Instance.getConfig().getInt("Books.CharacterLimit");
        int TotalBookChars = 0;

        if (enabled == false || charlimit < 0) return;

        for (String i : e.getNewBookMeta().getPages())
            TotalBookChars += i.length();

        if (TotalBookChars > charlimit) {
            e.getPlayer().sendMessage(C.chat(C.addPrefix("") + C.getSecondary("Please ") + C.getThird("do not attempt to write more than ") + C.getSecondary("" + charlimit) + C.getThird(" characters!")));
            e.setCancelled(true);
        }
    }
}
