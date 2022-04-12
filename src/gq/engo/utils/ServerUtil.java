package gq.engo.utils;

import gq.engo.Plugin;
import gq.engo.commands.VanishCommand;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.ShulkerBox;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import gq.engo.utils.handlers.MaterialHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class ServerUtil {
    public static void broadcast(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    public static void broadcastToOps(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isOp()) {
                p.sendMessage(message);
            }
        }
    }

    public static Player[] getOnline() {
        Player[] plrs = {};
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!VanishCommand.vanished.get(p)) {
                plrs[plrs.length + 1] = p;
            }
        }
        return plrs;
    }

    public static int getEntitesInChunk(Chunk c, EntityType e) {
        int amt = 0;
        for (Entity i : c.getEntities()) {
            if (i.getType() == e) {
                amt += 1;
            }
        }
        return amt;
    }

    public static void clearPotions(Player p, org.bukkit.potion.PotionEffect i, boolean isDuration) {
        String cause = "";

        if (isDuration) {
            cause = C.getThird("") + "(" + C.getSecondary("") + i.getType().getName() + " DURATION " + (i.getDuration()) + C.getThird(")");
        } else {
            cause = C.getThird("") + "(" + C.getSecondary("") + i.getType().getName() + " AMPLIFIER " + (i.getAmplifier()) + C.getThird(")");
        }

        p.removePotionEffect(i.getType());
        MaterialHandler.clearMaterials(p, Material.POTION);
        MaterialHandler.clearMaterials(p, Material.SPLASH_POTION);
        MaterialHandler.clearMaterials(p, Material.LINGERING_POTION);
        p.sendMessage(C.addPrefix("Detected impossible potion effect. \n" + cause));
    }

    public static boolean isShulker(ItemStack i) {
        try {
            Material t = i.getType();
            switch (t) {
                case PURPLE_SHULKER_BOX:
                    return true;
                case WHITE_SHULKER_BOX:
                    return true;
                case ORANGE_SHULKER_BOX:
                    return true;
                case MAGENTA_SHULKER_BOX:
                    return true;
                case LIGHT_BLUE_SHULKER_BOX:
                    return true;
                case YELLOW_SHULKER_BOX:
                    return true;
                case LIME_SHULKER_BOX:
                    return true;
                case PINK_SHULKER_BOX:
                    return true;
                case GRAY_SHULKER_BOX:
                    return true;
                case CYAN_SHULKER_BOX:
                    return true;
                case BLUE_SHULKER_BOX:
                    return true;
                case BROWN_SHULKER_BOX:
                    return true;
                case GREEN_SHULKER_BOX:
                    return true;
                case RED_SHULKER_BOX:
                    return true;
                case BLACK_SHULKER_BOX:
                    return true;
            }
        } catch (Exception e) {}
        return false;
    }

    public static Inventory getShulkerInventory(ItemStack i) {
        if (i.getItemMeta() instanceof BlockStateMeta) {
            ShulkerBox box = (ShulkerBox) ((BlockStateMeta) i.getItemMeta()).getBlockState();
            return box.getInventory();
        }
        return null;
    }

    public static boolean isIllegal(ItemStack item, boolean isInsideShulker) {
        if (item == null) return false;

        net.minecraft.server.v1_12_R1.ItemStack nmscopy = CraftItemStack.asNMSCopy(item);
        if (nmscopy.hasTag()) {
            NBTTagCompound tag = nmscopy.getTag();
            assert tag != null;
            if (tag.get("AttributeModifiers") != null) {
                return true;
            }
            if (tag.get("Unbreakable") != null) {
                return true;
            }
        }

        if (item.getType().equals(Material.FURNACE)
            || item.getType().equals(Material.CHEST)
            || item.getType().equals(Material.TRAPPED_CHEST)
            || item.getType().equals(Material.HOPPER)
            || item.getType().equals(Material.DISPENSER)
            || item.getType().equals(Material.DROPPER) )
        {
            NBTTagCompound tag = nmscopy.getTag();
            if (tag!=null) {
                return true;
            }
        }


        if (item.getType() == Material.LINGERING_POTION || item.getType() == Material.SPLASH_POTION || item.getType() == Material.POTION || item.getType() == Material.TIPPED_ARROW) {
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
            if (potionMeta.hasCustomEffects()) {
                for (PotionEffect effect : potionMeta.getCustomEffects()) {
                    if (effect.getDuration() > 12000) {
                        return true;
                    }
                    if (effect.getType() == PotionEffectType.DAMAGE_RESISTANCE) {
                        if (effect.getAmplifier() > 1) {
                            return true;
                        }
                    } else {
                        if (effect.getAmplifier() > 5) {
                            return true;
                        }
                    }
                }
            }
        }

        if (item.getDurability() > item.getType().getMaxDurability() && item.getType().getMaxDurability() > 0) {
            return true;
        };

        try {
            if (item.getEnchantments() != null) {
                Map<Enchantment, Integer> ench = item.getEnchantments();

                for (Map.Entry<Enchantment, Integer> entry : ench.entrySet()) {
                    Enchantment enchant = entry.getKey();
                    int enchantLvl = entry.getValue();

                    if (enchantLvl > enchant.getMaxLevel()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {}

        if (MaterialHandler.isIllegalMaterial(item.getType())) return true;

        if ((isShulker(item) && Plugin.Instance.getConfig().getBoolean("Illegals.RevertShulkers"))) {
            if (isInsideShulker) return true;

            for (ItemStack i : getShulkerInventory(item).getContents()) {
                if (isIllegal(i, true)) {
                    return true;
                }
            }
        }

        if ((item.getAmount() > item.getMaxStackSize()) && Plugin.Instance.getConfig().getBoolean("Illegals.RevertOverstacks")) return true;

        return false;
    }

    public static void clearIllegalsInInventory(Inventory inv, boolean isShulker) {
        for (ItemStack i : inv.getContents()) {
            if (isIllegal(i, isShulker)) {
                i.setAmount(0);
            }
        }
    }

    public static void clearIllegalsInInventory2(ItemStack inv, boolean isShulker) {
        for (ItemStack i : getShulkerInventory(inv).getContents()) {
            if (isIllegal(i, isShulker)) {
                inv.setAmount(0);
                break;
            }
        }
    }

    public static void clearIllegalsInInventoryWithShulkers(Inventory inventory) {
        for (ItemStack i : inventory.getContents()) {
            if (isShulker(i)) {
                clearIllegalsInInventory2(i, true);
            } else {
                if (isIllegal(i, false)) {
                    i.setAmount(0);
                }
            }
        }
    }

    public static void clearIllegalsInInv(Inventory inventory) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.RevertShulkers")) {
            clearIllegalsInInventoryWithShulkers(inventory);
        } else {
            clearIllegalsInInventory(inventory, false);
        }
    }

    public static boolean opCheck(Player p, String path) {
        return (!p.isOp() || Plugin.Instance.getConfig().getBoolean(path+".OPsBypass") == false);
    }
}
