package fr.draconiummc.draconiummod.objects.tools;

import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.utils.IHasModel;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ItemPickaxeMod extends ItemPickaxe implements IHasModel {

    public ItemPickaxeMod(String name, ToolMaterial material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabInit.DRACONIUM_TOOLS);
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        DraconiumMod.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        // Vérifie si la pioche est une pioche en Draconium
        if (stack.getItem() == ItemInit.DRACONIUM_PICKAXE) {
            // Vérifie si la pioche est enchantée avec Efficiency
            int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);

            // Si la pioche a l'enchantement Efficiency 5 et que le bloc est du Stone
            if (state.getBlock() == Blocks.STONE && efficiency >= 5) {
                return 1000.0F; // Vitesse très élevée pour détruire instantanément les blocs de Stone
            }

            // Si le bloc est du Stone et que la pioche est en Draconium (sans l'enchantement Efficiency 5)
            if (state.getBlock() == Blocks.STONE) {
                return super.getDestroySpeed(stack, state); // Retourne la vitesse normale de la pioche en Draconium
            }
        }

        // Si la pioche n'est pas en Draconium, retourne la vitesse de destruction normale pour les autres blocs
        return super.getDestroySpeed(stack, state);
    }
}

