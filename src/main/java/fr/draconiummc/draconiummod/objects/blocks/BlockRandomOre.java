package fr.draconiummc.draconiummod.objects.blocks;

import fr.draconiummc.draconiummod.init.BlockInit;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRandomOre extends Block {

    public BlockRandomOre(String name, Material material) {
        super(Material.ROCK);
        this.setCreativeTab(CreativeTabInit.DRACONIUM_RESOURCES);
        this.setUnlocalizedName("random_ore");
        this.setRegistryName("random_ore");
        this.setHardness(3.0F);
        this.setResistance(5.0F);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        if (!world.isRemote) {
            Random rand = new Random();
            ItemStack drop = getRandomOre(rand);

            if (!drop.isEmpty()) {
                spawnAsEntity(world, pos, drop);
            }
        }
        super.harvestBlock(world, player, pos, state, te, stack);
    }

    private ItemStack getRandomOre(Random rand) {
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

        return new ItemStack(ores[rand.nextInt(ores.length)]);
    }
}
