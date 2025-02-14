package fr.draconiummc.draconiummod.objects.blocks;

import fr.draconiummc.draconiummod.init.BlockInit;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRandomOre extends Block {


    public BlockRandomOre(String name, Material material) {
        super(material);
        this.setCreativeTab(CreativeTabInit.DRACONIUM_RESOURCES);
        this.setUnlocalizedName("random_ore");
        this.setRegistryName("random_ore");
        this.setHardness(3.0F);
        this.setResistance(5.0F);
    }

    @Override
    public void harvestBlock(World world, net.minecraft.entity.player.EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        if (!world.isRemote) {

            int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            Random rand = new Random();
            ItemStack drop = getRandomOre(rand, fortune);

            if (!drop.isEmpty()) {
                spawnAsEntity(world, pos, drop);
            }
        }
        super.harvestBlock(world, player, pos, state, te, stack);
    }

    /**
     * Retourne un ItemStack contenant un minerai choisi aléatoirement.
     * Les chances de tomber sur un minerai rare sont améliorées par le niveau de Fortune.
     *
     * @param rand    Instance Random
     * @param fortune Niveau de Fortune de l'outil utilisé
     * @return L'ItemStack correspondant au minerai choisi
     */
    private ItemStack getRandomOre(Random rand, int fortune) {
        Item[] ores = {
                Item.getItemFromBlock(Blocks.COAL_ORE),
                Item.getItemFromBlock(Blocks.IRON_ORE),
                Item.getItemFromBlock(Blocks.GOLD_ORE),
                Item.getItemFromBlock(Blocks.DIAMOND_ORE),
                Item.getItemFromBlock(Blocks.LAPIS_ORE),
                Item.getItemFromBlock(Blocks.REDSTONE_ORE),
                Item.getItemFromBlock(BlockInit.PYRONITE_ORE),
                Item.getItemFromBlock(BlockInit.DRACONIUM_ORE),
                Item.getItemFromBlock(BlockInit.NOXIUM_ORE)
        };

        double[] baseWeights = {
                40.0,  // CHARBON
                30.0,  // FER
                20.0,  // OR
                10.0,  // DIAMANT
                25.0,  // LAPIS-LAZULI
                25.0,  // REDSTONE
                5.0,   // PYRONITE
                1.5,   // DRACONIUM
                0.7    // NOXIUM
        };


        double[] adjustedWeights = new double[baseWeights.length];
        for (int i = 0; i < baseWeights.length; i++) {
            adjustedWeights[i] = baseWeights[i];
        }


        adjustedWeights[3] += fortune * 0.3;
        adjustedWeights[6] += fortune * 0.2;
        adjustedWeights[7] += fortune * 0.1;
        adjustedWeights[8] += fortune * 0.1;


        double totalWeight = 0;
        for (double weight : adjustedWeights) {
            totalWeight += weight;
        }


        double randomWeight = rand.nextDouble() * totalWeight; // Nombre entre 0 et totalWeight
        double currentSum = 0;
        for (int i = 0; i < adjustedWeights.length; i++) {
            currentSum += adjustedWeights[i];
            if (randomWeight < currentSum) {
                return new ItemStack(ores[i]);
            }
        }
        return ItemStack.EMPTY;
    }

}