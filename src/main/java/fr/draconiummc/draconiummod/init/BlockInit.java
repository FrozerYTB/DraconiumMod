package fr.draconiummc.draconiummod.init;

import fr.draconiummc.draconiummod.objects.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block DRACONIUM_BLOCK = new BlockBase("draconium_block", Material.IRON);
}
