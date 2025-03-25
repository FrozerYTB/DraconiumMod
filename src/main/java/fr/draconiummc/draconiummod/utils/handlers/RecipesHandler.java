package fr.draconiummc.draconiummod.utils.handlers;

import fr.draconiummc.draconiummod.init.BlockInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesHandler
{
    public static void registerRecipes() {
        System.out.println("[DraconiumMod] Enregistrement des recettes de cuisson...");

        System.out.println("PYRONITE_ORE: " + Block.REGISTRY.getNameForObject(BlockInit.PYRONITE_ORE));
        System.out.println("DRACONIUM_ORE: " + Block.REGISTRY.getNameForObject(BlockInit.DRACONIUM_ORE));
        System.out.println("NOXIUM_ORE: " + Block.REGISTRY.getNameForObject(BlockInit.NOXIUM_ORE));

        System.out.println("ItemBlock PYRONITE_ORE: " + Item.getItemFromBlock(BlockInit.PYRONITE_ORE));

        GameRegistry.addSmelting(BlockInit.PYRONITE_ORE, new ItemStack(ItemInit.PYRONITE_INGOT, 1), 5.0f);
        GameRegistry.addSmelting(BlockInit.DRACONIUM_ORE, new ItemStack(ItemInit.DRACONIUM_INGOT, 1), 5.0f);
        GameRegistry.addSmelting(BlockInit.NOXIUM_ORE, new ItemStack(ItemInit.NOXIUM_GEM, 1), 5.0f);
    }
}
