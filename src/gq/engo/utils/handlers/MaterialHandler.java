package gq.engo.utils.handlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MaterialHandler {

    public static boolean findMaterial(Material[] array, org.bukkit.Material find) {
        boolean isIn = false;
        for (Material i : array) {
            if (i == find) {
                isIn = true;
                break;
            }
        }
        return isIn;
    }

    public static void clearMaterials(Player p, Material item) {
        for (ItemStack i : p.getInventory()) {
            if (i == null) continue;
            if (i.getType().equals(item)) {
                p.getInventory().setItemInOffHand(null);
                p.getInventory().setItemInMainHand(null)    ;
                i.setAmount(0);
            }
        }
    }

    public static boolean isIllegalMaterial(Material item) {
        Material[] illegalblocks = new Material[]{
                Material.BEDROCK,
                Material.ENDER_PORTAL_FRAME,
                Material.BARRIER,
                Material.COMMAND,
                Material.COMMAND_MINECART,
                Material.COMMAND_CHAIN,
                Material.COMMAND_REPEATING,
                Material.STRUCTURE_BLOCK,
                Material.STRUCTURE_VOID,
                Material.MONSTER_EGG,
                Material.MOB_SPAWNER,
                Material.MONSTER_EGGS
        };

        if (findMaterial(illegalblocks, item)) {
            return true;
        }

        return false;
    }
}
