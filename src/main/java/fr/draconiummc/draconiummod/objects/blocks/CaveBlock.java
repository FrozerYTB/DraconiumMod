package fr.draconiummc.draconiummod.objects.blocks;

import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.init.BlockInit;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.utils.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CaveBlock extends Block implements IHasModel
{
    public CaveBlock(String name, Material material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setHardness(5.0f);
        setCreativeTab(CreativeTabInit.DRACONIUM_BLOCKS);
        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels()
    {
        DraconiumMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}