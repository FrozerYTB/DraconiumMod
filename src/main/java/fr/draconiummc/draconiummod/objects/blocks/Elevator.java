package fr.draconiummc.draconiummod.objects.blocks;

import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.init.BlockInit;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.utils.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Elevator extends Block implements IHasModel
{
    public Elevator(String name, Material material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabInit.DRACONIUM_BLOCKS);
        setHardness(8.0F);
        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public void registerModels()
    {
        DraconiumMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if(entityIn instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)entityIn;
            if(player.isSneaking())
            {
                for(int i = 256 - pos.getY(); i > 0; i--)
                {
                    BlockPos blockPos = new BlockPos(pos.getX(), i, pos.getZ());

                    if(worldIn.getBlockState(blockPos).getBlock() == BlockInit.ELEVATOR)
                    {
                        player.attemptTeleport(player.posX, blockPos.getY() +1, player.posZ);
                    }
                }
            }
        }
    }
}
