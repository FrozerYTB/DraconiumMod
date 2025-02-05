package fr.draconiummc.draconiummod.objects.tools;

import java.util.Set;

import com.google.common.collect.Sets;

import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.utils.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class ItemAxeMod extends ItemAxe implements IHasModel
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(
            Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST,
            Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER,
            Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE
    );

    public ItemAxeMod(String name, ToolMaterial material)
    {
        // ⚠️ Problème corrigé : Ajout des valeurs d'attaque et de vitesse correctes
        super(material, material.getAttackDamage(), -3.0F);

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabInit.DRACONIUM_TOOLS);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Material material = state.getMaterial();
        return EFFECTIVE_ON.contains(state.getBlock()) ? this.efficiency : 1.0F;
    }

    @Override
    public void registerModels()
    {
        DraconiumMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
