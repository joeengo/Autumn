package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.handlers.MaterialHandler;
import gq.engo.utils.*;
import io.netty.util.Attribute;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.UUID;

public class Antillegal implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.Enabled") == false) return;
        Player plr = e.getPlayer();
        Material type = e.getBlock().getType();
        if (MaterialHandler.isIllegalMaterial(type) && e.getItemInHand().getType() != Material.EYE_OF_ENDER) {
            //e.getBlock().setType(Material.AIR);
            e.setBuild(false);
            e.setCancelled(true);
        }

        if (ServerUtil.isIllegal(e.getItemInHand(), false)) {
            plr.sendMessage(C.chat(C.addPrefix("Illegal item! ("+ServerUtil.getIllegalReason(e.getItemInHand(), false)+")")));
            //e.getBlock().setType(Material.AIR);
            e.setBuild(false);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER || Plugin.Instance.getConfig().getBoolean("Anti32k.Enabled") == false) return;
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        double damage = event.getDamage();
        ItemStack offhanditem = damager.getServer().getPlayer(damager.getName()).getInventory().getItemInOffHand();
        ItemStack mainhanditem = damager.getServer().getPlayer(damager.getName()).getInventory().getItemInMainHand();
        if (damage > 30.0D && ServerUtil.opCheck(Bukkit.getPlayer(damager.getUniqueId()), "Anti32k")) {
            damager.sendMessage(C.addPrefix("Detected impossible damage. " + C.getThird("") + "(" + C.getSecondary("") + (int)damage + " dmg" + C.getThird(")")));
            event.setCancelled(true);
            if (offhanditem.getType() == mainhanditem.getType()) {
                offhanditem.setAmount(0);
            }
            mainhanditem.setAmount(0);
        }
    }

    @EventHandler
    public void onItemSwap(PlayerItemHeldEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.Enabled") == false) return;
        ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());
        if (ServerUtil.isIllegal(newItem, false) && (ServerUtil.opCheck(e.getPlayer(), "Illegals")) ) {
            newItem.setAmount(0);
            e.getPlayer().sendMessage(C.chat(C.addPrefix("Illegal item! ("+ServerUtil.getIllegalReason(newItem, false)+")")));
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.Enabled") == false) return;
        if (ServerUtil.opCheck(e.getPlayer(), "Illegals")) {
            if (ServerUtil.isIllegal(e.getItemDrop().getItemStack(), false)) {
                e.getItemDrop().remove();
                e.getPlayer().sendMessage(C.chat(C.addPrefix("Illegal item! ("+ServerUtil.getIllegalReason(e.getItemDrop().getItemStack(), false)+")")));
            }
        }
    }

    @EventHandler
    public void onInventory(InventoryOpenEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.Enabled") == false) return;
        if (ServerUtil.opCheck(Bukkit.getPlayer(e.getPlayer().getUniqueId()), "Illegals")) {
            for (ItemStack item : e.getInventory()) {
                if (ServerUtil.isIllegal(item, false)) {
                    e.getPlayer().sendMessage(C.chat(C.addPrefix("Illegal item! ("+ServerUtil.getIllegalReason(item, false)+")")));
                    item.setAmount(0);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.Enabled") == false) return;
        if (ServerUtil.opCheck(Bukkit.getPlayer(e.getPlayer().getUniqueId()), "Illegals")) {
            for (ItemStack item : e.getInventory()) {
                if (ServerUtil.isIllegal(item, false)) {
                    e.getPlayer().sendMessage(C.chat(C.addPrefix("Illegal item! ("+ServerUtil.getIllegalReason(item, false)+")")));
                    item.setAmount(0);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick (InventoryClickEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.Enabled") == false) return;
        UUID playerUID = e.getWhoClicked().getUniqueId();
        Player p = Bukkit.getPlayer(playerUID);

        if (ServerUtil.opCheck(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()), "Illegals")) {
            for (ItemStack item : e.getInventory()) {
                if (ServerUtil.isIllegal(item, false)) {
                    p.sendMessage(C.chat(C.addPrefix("Illegal item! ("+ServerUtil.getIllegalReason(item, false)+")")));
                    item.setAmount(0);
                }
            }
        }


    }

    @EventHandler
    public void onPickup (EntityPickupItemEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.Enabled") == false) return;
        if (e.getEntity().getType() != EntityType.PLAYER) return;
        if (ServerUtil.opCheck(Bukkit.getPlayer(e.getEntity().getUniqueId()), "Illegals")) {
            if (ServerUtil.isIllegal(e.getItem().getItemStack(), false)) {
                e.setCancelled(true);
                e.getItem().remove();
                Player p = (Player) e.getEntity();
                p.sendMessage(C.chat(C.addPrefix("Illegal item! ("+ServerUtil.getIllegalReason(e.getItem().getItemStack(), false)+")")));
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.Enabled") == false) return;
        // anti potion illegal thing
        if (Plugin.Instance.getConfig().getBoolean("Illegals.RevertPotionEffects") && ((Plugin.Instance.getConfig().getBoolean("Illegals.OPsBypass") && !e.getPlayer().isOp()) || !Plugin.Instance.getConfig().getBoolean("Illegals.OPsBypass"))) {
            PotionEffect[] potions = e.getPlayer().getActivePotionEffects().toArray(new org.bukkit.potion.PotionEffect[0]);
            for (PotionEffect i : potions) {
                if (i.getType() == PotionEffectType.DAMAGE_RESISTANCE) {
                    if (i.getAmplifier() > 1) {
                        ServerUtil.clearPotions(e.getPlayer(), i, false);
                        continue;
                    }
                } else {
                    if (i.getAmplifier() > 5) {
                        ServerUtil.clearPotions(e.getPlayer(), i, false);
                        continue;
                    }
                }

                if (i.getDuration() > 12000) {
                    ServerUtil.clearPotions(e.getPlayer(), i, true);
                }
            }
        }
    }
}
