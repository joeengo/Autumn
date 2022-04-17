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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.Objects;

public class ServerUtil {

    public static boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    public static String formatTime(long milli) {
        long seconds = -milli / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return days + "d " + hours % 24 + "h " + minutes % 60 + "m " + seconds % 60 + "s";
    }

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

    public static int getOnline() {
        int plrs = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!isVanished(p) && (VanishCommand.vanished.get(p) == null || VanishCommand.vanished.get(p)==false) ) {
                plrs = plrs + 1;
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
        String cause;

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
                case WHITE_SHULKER_BOX:
                case ORANGE_SHULKER_BOX:
                case MAGENTA_SHULKER_BOX:
                case LIGHT_BLUE_SHULKER_BOX:
                case YELLOW_SHULKER_BOX:
                case LIME_SHULKER_BOX:
                case PINK_SHULKER_BOX:
                case GRAY_SHULKER_BOX:
                case CYAN_SHULKER_BOX:
                case BLUE_SHULKER_BOX:
                case BROWN_SHULKER_BOX:
                case GREEN_SHULKER_BOX:
                case RED_SHULKER_BOX:
                case BLACK_SHULKER_BOX:
                    return true;
            }
        } catch (Exception ignored) {}
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

        if (item.getType().equals(Material.SKULL) || item.getType().equals(Material.SKULL_ITEM)) {
            SkullMeta sk = (SkullMeta) item.getItemMeta();
            if (sk.getOwningPlayer() != null) {
                return true;
            }
        }

        if (item.getType().equals(Material.FURNACE) // patch items put inside of storages while in ItemStack state
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
        }

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
        } catch (Exception ignored) {}

        if (MaterialHandler.isIllegalMaterial(item.getType())) return true;

        if ((isShulker(item) && Plugin.Instance.getConfig().getBoolean("Illegals.RevertShulkers"))) {
            if (isInsideShulker) return true;

            for (ItemStack i : Objects.requireNonNull(getShulkerInventory(item)).getContents()) {
                if (isIllegal(i, true)) {
                    return true;
                }
            }
        }

        return (item.getAmount() > item.getMaxStackSize()) && Plugin.Instance.getConfig().getBoolean("Illegals.RevertOverstacks");
    }

    public static String getIllegalReason(ItemStack item, boolean isInsideShulker) {
        if (item == null) return "Unknown";

        net.minecraft.server.v1_12_R1.ItemStack nmscopy = CraftItemStack.asNMSCopy(item);
        if (nmscopy.hasTag()) {
            NBTTagCompound tag = nmscopy.getTag();
            assert tag != null;
            if (tag.get("AttributeModifiers") != null) {
                return "Attribute Modifiers";
            }
            if (tag.get("Unbreakable") != null) {
                return "Unbreakable";
            }
        }

        if (item.getType().equals(Material.SKULL) || item.getType().equals(Material.SKULL_ITEM)) {
            SkullMeta sk = (SkullMeta) item.getItemMeta();
            if (sk.getOwningPlayer() != null) {
                return "Player head";
            }
        }

        if (item.getType().equals(Material.FURNACE) // patch items put inside of storages while in ItemStack state
                || item.getType().equals(Material.CHEST)
                || item.getType().equals(Material.TRAPPED_CHEST)
                || item.getType().equals(Material.HOPPER)
                || item.getType().equals(Material.DISPENSER)
                || item.getType().equals(Material.DROPPER) )
        {
            NBTTagCompound tag = nmscopy.getTag();
            if (tag!=null) {
                return "NBT Data invalid";
            }
        }


        if (item.getType() == Material.LINGERING_POTION || item.getType() == Material.SPLASH_POTION || item.getType() == Material.POTION || item.getType() == Material.TIPPED_ARROW) {
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
            if (potionMeta.hasCustomEffects()) {
                for (PotionEffect effect : potionMeta.getCustomEffects()) {
                    if (effect.getDuration() > 12000) {
                        return "Illegal potion duration";
                    }
                    if (effect.getType() == PotionEffectType.DAMAGE_RESISTANCE) {
                        if (effect.getAmplifier() > 1) {
                            return "Illegal potion amplifier";
                        }
                    } else {
                        if (effect.getAmplifier() > 5) {
                            return "Illegal potion amplifier";
                        }
                    }
                }
            }
        }

        if (item.getDurability() > item.getType().getMaxDurability() && item.getType().getMaxDurability() > 0) {
            return "Illegal durability";
        }

        try {
            if (item.getEnchantments() != null) {
                Map<Enchantment, Integer> ench = item.getEnchantments();

                for (Map.Entry<Enchantment, Integer> entry : ench.entrySet()) {
                    Enchantment enchant = entry.getKey();
                    int enchantLvl = entry.getValue();

                    if (enchantLvl > enchant.getMaxLevel()) {
                        return "Illegal enchant";
                    }
                }
            }
        } catch (Exception ignored) {}

        if (MaterialHandler.isIllegalMaterial(item.getType())) return "Illegal block";

        if ((isShulker(item) && Plugin.Instance.getConfig().getBoolean("Illegals.RevertShulkers"))) {
            if (isInsideShulker) return "Shulker inside shulker";

            for (ItemStack i : Objects.requireNonNull(getShulkerInventory(item)).getContents()) {
                if (isIllegal(i, true)) {
                    return getIllegalReason(i, true);
                }
            }
        }

        if ((item.getAmount() > item.getMaxStackSize()) && Plugin.Instance.getConfig().getBoolean("Illegals.RevertOverstacks")) return "Overstack";

        return "Unknown";
    }



    public static boolean opCheck(Player p, String path) {
        return (!p.isOp() || !Plugin.Instance.getConfig().getBoolean(path + ".OPsBypass"));
    }

    public static String RAM(long l) {
        return l / (1024 * 1024) + "";
    }
}
