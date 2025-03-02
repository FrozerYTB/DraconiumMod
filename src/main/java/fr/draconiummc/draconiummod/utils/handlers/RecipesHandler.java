package fr.draconiummc.draconiummod.utils.handlers;

import fr.draconiummc.draconiummod.init.BlockInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesHandler
{
    public static void registerRecipes()
    {
        GameRegistry.addSmelting(BlockInit.PYRONITE_ORE, new ItemStack(ItemInit.PYRONITE_INGOT, 1), 5.0f);
        GameRegistry.addSmelting(BlockInit.DRACONIUM_ORE, new ItemStack(ItemInit.DRACONIUM_INGOT, 1), 5.0f);
        GameRegistry.addSmelting(BlockInit.NOXIUM_ORE, new ItemStack(ItemInit.NOXIUM_GEM, 1), 5.0f);


    }
}
